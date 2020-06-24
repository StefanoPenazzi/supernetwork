/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import org.matsim.api.core.v01.population.Plan;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalPlanModelFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp.OrdaRomOptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class PlanManagerFactoryTdspIntermodal implements PlanManagerFactory {

	private final PlanModelFactory planModelFactory;
	
	@Inject
	public PlanManagerFactoryTdspIntermodal(PlanModelFactory planModelFactory) {
		this.planModelFactory = planModelFactory;
	}
	
	@Override
	public PlanManager createPlanManager(Plan plan) {
		TdspIntermodalGraph planModel = (TdspIntermodalGraph) this.planModelFactory.createPlanModel(plan);
		return new PlanManagerTdspIntermodal(planModel, new OrdaRomOptimizationAlgorithm());
	}

}
