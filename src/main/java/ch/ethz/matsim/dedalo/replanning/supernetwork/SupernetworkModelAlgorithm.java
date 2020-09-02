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

	private final SupernetworkStrategyModel supernetworkStrategyModel;
	
	public SupernetworkModelAlgorithm(SupernetworkStrategyModel supernetworkStrategyModel) {
		this.supernetworkStrategyModel =  supernetworkStrategyModel;
		
	}
	@Override
	public void run(Plan plan) {
		// change the plan with the result of the model
		plan = this.supernetworkStrategyModel.newPlan(plan.getPerson());
		//System.out.println("...");
	}

}
