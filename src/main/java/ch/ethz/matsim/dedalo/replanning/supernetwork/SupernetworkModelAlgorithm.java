/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import java.util.List;

import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.population.PopulationUtils;
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
		Plan plan1 = this.supernetworkStrategyModel.newPlan(plan.getPerson());
		if(plan1 != null) {
			plan.getPlanElements().clear();
			for (PlanElement pe: plan1.getPlanElements()) {
				plan.getPlanElements().add(pe);
			}
		}
		else {
		   //System.out.println("...");
		}
	}

}
