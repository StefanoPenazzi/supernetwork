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

import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanManagerFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModelFactory;
import gnu.trove.list.array.TDoubleArrayList;

/**
 * @author stefanopenazzi
 *
 */
public class PlansForPopulationContainerImpl implements PlansForPopulationContainer {

	private final Map<Id<Person>, PlanManager> planManagerContainer = new HashMap<>();
	
	@Inject
	public PlansForPopulationContainerImpl(){

	}
	
	@Override
	public PlanManager getPlanManagerForAgent(final Id<Person> agentId) {
		return this.planManagerContainer.get(agentId);
	}

	@Override
	public void addPlanManager(Id<Person> id, PlanManager planManager) {
		this.planManagerContainer.put(id,planManager);
	}


}
