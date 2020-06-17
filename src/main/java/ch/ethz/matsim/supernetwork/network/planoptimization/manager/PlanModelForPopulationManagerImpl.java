/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import java.util.List;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.planoptimization.containers.PlanModelForPopulationContainer;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;

/**
 * @author stefanopenazzi
 *
 */
public class PlanModelForPopulationManagerImpl implements PlanModelForPopulationManager{
	
	private final PlanModelForPopulationContainer planModelForPopulationContainer;
	private final Population population;
	
	@Inject
	public PlanModelForPopulationManagerImpl(PlanModelForPopulationContainer planModelForPopulationContainer, Population population) {
		this.planModelForPopulationContainer = planModelForPopulationContainer;
		this.population = population;
	}

	@Override
	public void populationNewPlans() {
		for (Person person : this.population.getPersons().values()) {
			//it is probably better to have this is the replanning strategy but the scoring becomes useless
			List<? extends PlanElement> pe = planModelForPopulationContainer.getPlanModelForAgent(person.getId()).getNewPlan();
		}
	}

	@Override
	public void init() {
		this.planModelForPopulationContainer.init();
		
	}

}
