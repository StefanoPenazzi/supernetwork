/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import java.util.List;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.planoptimization.containers.PlansForPopulationContainer;


/**
 * @author stefanopenazzi
 *
 */
public class PlansForPopulationManagerImpl implements PlansForPopulationManager{
	
	private final PlansForPopulationContainer plansForPopulationContainer;
	private final Population population;
	
	@Inject
	public PlansForPopulationManagerImpl(PlansForPopulationContainer plansForPopulationContainer, Population population) {
		this.plansForPopulationContainer = plansForPopulationContainer;
		this.population = population;
	}

	@Override
	public void populationNewPlans() {
		for (Person person : this.population.getPersons().values()) {
			//it is probably better to have this is the replanning strategy but the scoring becomes useless
			List<? extends PlanElement> pe = plansForPopulationContainer.getPlanModelForAgent(person.getId()).getNewPlan();
		}
	}

	@Override
	public void init() {
		this.plansForPopulationContainer.init();
		
	}

}
