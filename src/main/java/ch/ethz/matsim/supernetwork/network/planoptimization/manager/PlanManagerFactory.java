/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import org.matsim.api.core.v01.population.Plan;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanManagerFactory {

	public PlanManager createPlanManager(Plan plan);
}
