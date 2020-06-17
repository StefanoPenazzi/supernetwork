/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models;

import java.util.List;


import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanModel {

	public OptimizationAlgorithm getOptimizationAlgorithm();
	public List<? extends PlanElement> getNewPlan();
}
