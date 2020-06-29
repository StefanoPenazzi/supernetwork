/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import java.util.List;
import org.matsim.api.core.v01.population.PlanElement;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp.OrdaRomOptimizationAlgorithm;

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
	public List<? extends PlanElement> getNewPlan() {
		this.optimizationAlgorithm.run(planModel);
		return null;
	}

}
