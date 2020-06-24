/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import org.matsim.api.core.v01.population.Plan;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalPlanModelFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp.OrdaRomOptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class PlanManagerFactoryTdspIntermodal implements PlanManagerFactory {

	@Inject TdspIntermodalPlanModelFactory tdspIntermodalPlanModelFactory;
	
	@Inject
	public PlanManagerFactoryTdspIntermodal() {
		
	}
	
	@Override
	public PlanManager createPlanManager(Plan plan) {
		TdspIntermodalGraph planModel = tdspIntermodalPlanModelFactory.createPlanModel(plan);
		return new PlanManagerTdspIntermodal(planModel, new OrdaRomOptimizationAlgorithm());
	}

}
