/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.network.planoptimization.containers.PlanModelForPopulationContainerImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanModelForPopulationManagerImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp.TdspOrdaRomPlanModelFactory;

/**
 * @author stefanopenazzi
 *
 */
public class PlanOptimizationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		bindPlanModelForPopulationContainer().to(PlanModelForPopulationContainerImpl.class).asEagerSingleton();
		bindPlanModelForPopulationManager().to(PlanModelForPopulationManagerImpl.class).asEagerSingleton();
		
		bindPlanModelFactory().to(TdspOrdaRomPlanModelFactory.class);
		
		
		bind(ScoringFunctionsForPopulationGraph.class).asEagerSingleton();
		
		
	}

}
