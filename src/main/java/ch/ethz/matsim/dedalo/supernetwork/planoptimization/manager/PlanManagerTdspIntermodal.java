/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager;

import java.util.List;

import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.graph.tdsp.OrdaRomOptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class PlanManagerTdspIntermodal implements PlanManager {

	private final TdspIntermodalGraph planModel;
	private final OrdaRomOptimizationAlgorithm optimizationAlgorithm;
	
	public PlanManagerTdspIntermodal(TdspIntermodalGraph planModel,OrdaRomOptimizationAlgorithm optimizationAlgorithm) {
		this.planModel = planModel;
		this.optimizationAlgorithm = optimizationAlgorithm;
	}

	@Override
	public Plan getNewPlan() {
		return this.optimizationAlgorithm.run(planModel);
	}

}
