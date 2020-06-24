/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanModelFactory {
	
	public PlanModel createPlanModel(Plan plan);

}
