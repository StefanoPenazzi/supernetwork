/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import java.util.List;

import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class TdspGraphOrdaRom extends GraphImpl {

	/**
	 * @param optimizationAlgorithm
	 */
	public TdspGraphOrdaRom(OptimizationAlgorithm optimizationAlgorithm) {
		super(optimizationAlgorithm);
		// TODO Auto-generated constructor stub;
	}

	@Override
	public List<? extends PlanElement> getNewPlan() {
		return this.getOptimizationAlgorithm().run(this);
	}

}
