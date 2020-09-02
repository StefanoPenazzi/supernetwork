/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms;

import java.util.List;

import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModel;

/**
 * @author stefanopenazzi
 *
 */
public interface OptimizationAlgorithm {

	public Plan run(PlanModel planModel);
}
