/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.containers;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Person;

import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;

/**
 * @author stefanopenazzi
 *
 */
public interface PlansForPopulationContainer {

	public PlanManager getPlanManagerForAgent(final Id<Person> agentId);
	public void addPlanManager(Id<Person> id, PlanManager planManager);
}
