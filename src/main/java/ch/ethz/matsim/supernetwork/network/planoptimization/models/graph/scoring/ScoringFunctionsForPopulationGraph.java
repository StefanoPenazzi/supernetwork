/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.controler.ControlerListenerManager;
import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;
import org.matsim.core.events.algorithms.Vehicle2DriverEventHandler;
import org.matsim.core.scoring.EventsToActivities;
import org.matsim.core.scoring.EventsToLegs;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;

import com.google.inject.Inject;

/**
 * @author stefanopenazzi
 *
 */
public class ScoringFunctionsForPopulationGraph {
	  
	    private final Population population;
		private final ScoringFunctionFactory scoringFunctionFactory;
		
		private final TreeMap<Id<Person>, ScoringFunction> agentScorers = new TreeMap<Id<Person>,ScoringFunction>();
		private final AtomicReference<Throwable> exception = new AtomicReference<>();
		
		private Vehicle2DriverEventHandler vehicles2Drivers = new Vehicle2DriverEventHandler();

		@Inject
		public ScoringFunctionsForPopulationGraph( ControlerListenerManager controlerListenerManager, EventsManager eventsManager, EventsToActivities eventsToActivities, EventsToLegs eventsToLegs,
							 Population population, ScoringFunctionFactory scoringFunctionFactory) {
			
			this.population = population;
			this.scoringFunctionFactory = scoringFunctionFactory;
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


}
