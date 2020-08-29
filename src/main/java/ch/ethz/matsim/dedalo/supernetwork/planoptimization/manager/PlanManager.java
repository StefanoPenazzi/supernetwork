/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager;

import java.util.List;

import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModel;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanManager {
	
	public boolean getNewPlan();

}
