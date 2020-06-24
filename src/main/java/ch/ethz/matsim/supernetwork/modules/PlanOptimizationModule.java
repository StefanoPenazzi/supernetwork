/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.network.planoptimization.containers.PlansForPopulationContainerImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanManagerFactoryTdspIntermodal;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlansForPopulationManagerImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalPlanModelFactory;

/**
 * @author stefanopenazzi
 *
 */
public class PlanOptimizationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		bindPlansForPopulationContainer().to(PlansForPopulationContainerImpl.class).asEagerSingleton();
		bindPlansForPopulationManager().to(PlansForPopulationManagerImpl.class).asEagerSingleton();
		
		bindPlanModelFactory().to(TdspIntermodalPlanModelFactory.class);
		bindPlanManagerFactory().to(PlanManagerFactoryTdspIntermodal.class);
		
		
		bind(ScoringFunctionsForPopulationGraph.class).asEagerSingleton();
		
		
	}

}
