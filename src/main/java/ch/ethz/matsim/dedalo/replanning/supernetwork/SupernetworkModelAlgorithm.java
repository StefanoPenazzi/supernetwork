/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.population.algorithms.PlanAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkModelAlgorithm implements PlanAlgorithm{

	private final SupernetworkModel supernetworkModel;
	
	public SupernetworkModelAlgorithm(SupernetworkModel supernetworkModel) {
		this.supernetworkModel =  supernetworkModel;
		
	}
	
	
	@Override
	public void run(Plan plan) {
		// change the plan with the result of the model
		this.supernetworkModel.newPlan(plan);
		
	}

}
