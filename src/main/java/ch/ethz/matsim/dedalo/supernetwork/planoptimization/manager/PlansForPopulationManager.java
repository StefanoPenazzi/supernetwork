/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;

/**
 * @author stefanopenazzi
 *
 */
public interface PlansForPopulationManager {
	
	public void populationNewPlans();
	public Plan personNewPlan(Person person);
	public void init();

}
