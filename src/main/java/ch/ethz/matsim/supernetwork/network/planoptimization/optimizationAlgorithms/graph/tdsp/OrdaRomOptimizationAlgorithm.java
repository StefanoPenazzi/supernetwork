/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp;

import java.util.List;

import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp.TdspGraphOrdaRom;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class OrdaRomOptimizationAlgorithm implements OptimizationAlgorithm {

	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	private final ContainerManager containerManager;
	
	public OrdaRomOptimizationAlgorithm(ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,ContainerManager containerManager) {
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		this.containerManager = containerManager;
	}
	
	@Override
	public List<? extends PlanElement> run(PlanModel planModel) {
		TdspIntermodalGraph graph = (TdspIntermodalGraph)planModel;
		
		return null;
	}

}
