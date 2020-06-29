/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.controler.ControlerListenerManager;
import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.IterationStartsListener;
import org.matsim.core.controler.listener.StartupListener;
import org.matsim.core.events.algorithms.Vehicle2DriverEventHandler;
import org.matsim.core.scoring.EventsToActivities;
import org.matsim.core.scoring.EventsToLegs;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;
import org.matsim.core.scoring.functions.ScoringParameters;
import org.matsim.core.scoring.functions.ScoringParametersForPerson;
import org.matsim.core.utils.misc.Time;

import com.google.inject.Inject;

/**
 * @author stefanopenazzi
 *
 */
public class ScoringFunctionsForPopulationGraph {
	  
	    private final Population population;
		private final ScoringFunctionFactory scoringFunctionFactory;
		private final ScoringParametersForPerson params;
		
		private final TreeMap<Id<Person>, ScoringFunction> agentScorers = new TreeMap<Id<Person>,ScoringFunction>();
		private final AtomicReference<Throwable> exception = new AtomicReference<>();
		
		private Vehicle2DriverEventHandler vehicles2Drivers = new Vehicle2DriverEventHandler();

		@Inject
		public ScoringFunctionsForPopulationGraph( ControlerListenerManager controlerListenerManager, EventsManager eventsManager, EventsToActivities eventsToActivities, EventsToLegs eventsToLegs,
							 Population population, ScoringFunctionFactory scoringFunctionFactory,ScoringParametersForPerson params) {
			
			this.population = population;
			this.scoringFunctionFactory = scoringFunctionFactory;
			this.params = params;
		}

		public void init() {
			for (Person person : this.population.getPersons().values()) {
				ScoringFunction data = this.scoringFunctionFactory.createNewScoringFunction(person);
				this.agentScorers.put(person.getId(), data);
			}
		}
		
		public ScoringFunction getScoringFunctionForAgent(final Id<Person> agentId) {
			return this.agentScorers.get(agentId);
		}
		
		public double getActivityUtilityFunctionValueForAgent(final Person person, Activity activity, double duration) {
			ScoringParameters parameters = params.getScoringParameters( person );
			double openingTime = parameters.utilParams.get(activity.getType()).getOpeningTime();
			openingTime = (Time.isUndefinedTime(openingTime)) ? 0 : openingTime;
			activity.setMaximumDuration(duration);
			activity.setStartTime(openingTime);
			activity.setEndTime(openingTime+duration);
			ScoringFunction scoringFunction = this.agentScorers.get(person.getId());
 			double oldScore = scoringFunction.getScore();
 			scoringFunction.handleActivity(activity);
 			double newScore = scoringFunction.getScore();
			return newScore - oldScore;
		}
		
		public double getLegUtilityFunctionValueForAgent(Person person, Leg leg) {
			ScoringFunction scoringFunction = this.agentScorers.get(person.getId());
 			double oldScore = scoringFunction.getScore();
 			scoringFunction.handleLeg(leg);
 			double newScore = scoringFunction.getScore();
			return newScore - oldScore;
		}
		
		
}
