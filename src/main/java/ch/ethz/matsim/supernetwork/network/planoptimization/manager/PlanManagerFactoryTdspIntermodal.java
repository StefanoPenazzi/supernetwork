/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import org.matsim.api.core.v01.population.Plan;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalPlanModelFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp.OrdaRomOptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class PlanManagerFactoryTdspIntermodal implements PlanManagerFactory {

	private final PlanModelFactory planModelFactory;
	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	private final ContainerManager containerManager;
	
	@Inject
	public PlanManagerFactoryTdspIntermodal(PlanModelFactory planModelFactory, ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,
			ContainerManager containerManager) {
		this.planModelFactory = planModelFactory;
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		this.containerManager = containerManager;
	}
	
	@Override
	public PlanManager createPlanManager(Plan plan) {
		TdspIntermodalGraph planModel = (TdspIntermodalGraph) this.planModelFactory.createPlanModel(plan);
		return new PlanManagerTdspIntermodal(planModel, new OrdaRomOptimizationAlgorithm(this.scoringFunctionForPopulationGraph,this.containerManager));
	}

}
