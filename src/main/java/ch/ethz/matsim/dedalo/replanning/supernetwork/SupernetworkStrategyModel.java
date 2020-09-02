/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkStrategyModel {
	public Plan newPlan(Person person);
}
