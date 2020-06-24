/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import java.util.List;

import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanManager {
	
	public List<? extends PlanElement> getNewPlan();

}
