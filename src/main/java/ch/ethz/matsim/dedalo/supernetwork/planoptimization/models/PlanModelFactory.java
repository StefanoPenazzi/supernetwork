/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanModelFactory {
	
	public PlanModel createPlanModel(Person person);
	public Plan convertPlanForModel(Plan plan);

}
