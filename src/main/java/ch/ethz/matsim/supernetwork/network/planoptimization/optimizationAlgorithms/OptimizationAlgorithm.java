/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms;

import java.util.List;

import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;

/**
 * @author stefanopenazzi
 *
 */
public interface OptimizationAlgorithm {

	public List<? extends PlanElement> run(PlanModel planModel);
}
