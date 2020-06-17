/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.containers;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Person;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanModelForPopulationContainer {

	public void init();
	public PlanModel getPlanModelForAgent(final Id<Person> agentId);
}
