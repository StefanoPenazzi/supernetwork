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
	private final PlanManagerFactory planManagerFactory;
	
	@Inject
	public PlansForPopulationManagerImpl(PlansForPopulationContainer plansForPopulationContainer, Population population,PlanManagerFactory planManagerFactory) {
		this.plansForPopulationContainer = plansForPopulationContainer;
		this.population = population;
		this.planManagerFactory = planManagerFactory;
	}

	@Override
	public void populationNewPlans() {
		//TODO
		for (Person person : this.population.getPersons().values()) {
			//it is probably better to have this in the replanning strategy but the scoring becomes useless
			List<? extends PlanElement> pe = plansForPopulationContainer.getPlanManagerForAgent(person.getId()).getNewPlan();
		}
	}

	@Override
	public void init() {
		for (Person person : this.population.getPersons().values()) {
			PlanManager planManager = this.planManagerFactory.createPlanManager(person.getPlans().get(0));
			this.plansForPopulationContainer.addPlanManager(person.getId(), planManager);
		}
		System.out.println("");
	}

}
