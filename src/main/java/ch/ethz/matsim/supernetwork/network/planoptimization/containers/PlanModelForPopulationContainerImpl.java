/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.containers;

import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.scoring.ScoringFunction;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModelFactory;
import gnu.trove.list.array.TDoubleArrayList;

/**
 * @author stefanopenazzi
 *
 */
public class PlanModelForPopulationContainerImpl implements PlanModelForPopulationContainer {
	
	private final Population population;
	private final Map<Id<Person>, PlanModel> agentPlanModels = new HashMap<>();
	private final PlanModelFactory planModelFactory;
	
	@Inject
	public PlanModelForPopulationContainerImpl(Population population,  PlanModelFactory planModelFactory){
		this.population = population;
		this.planModelFactory = planModelFactory;
	}
	
	@Override
	public void init() {
		for (Person person : this.population.getPersons().values()) {
			PlanModel planModel = this.planModelFactory.createPlanModel(person.getPlans().get(0));
			this.agentPlanModels.put(person.getId(), planModel);
		}
		System.out.println("");
	}
	
	@Override
	public PlanModel getPlanModelForAgent(final Id<Person> agentId) {
		return this.agentPlanModels.get(agentId);
	}


}
