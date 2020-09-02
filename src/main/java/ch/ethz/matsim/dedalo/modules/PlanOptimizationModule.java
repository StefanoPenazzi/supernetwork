/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import ch.ethz.matsim.dedalo.supernetwork.events.SupernetworkInitialization;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.containers.PlansForPopulationContainerImpl;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlanManagerFactoryTdspIntermodal;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlansForPopulationManagerImpl;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal.TdspIntermodalPlanModelFactory;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManagerImpl;

/**
 * @author stefanopenazzi
 *
 */
public class PlanOptimizationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		bindActivityManager().to(ActivityManagerImpl.class).asEagerSingleton();
		
		this.addControlerListenerBinding().to(SupernetworkInitialization.class);
		
		bindPlansForPopulationContainer().to(PlansForPopulationContainerImpl.class).asEagerSingleton();
		bindPlansForPopulationManager().to(PlansForPopulationManagerImpl.class).asEagerSingleton();
		
		bindPlanModelFactory().to(TdspIntermodalPlanModelFactory.class);
		bindPlanManagerFactory().to(PlanManagerFactoryTdspIntermodal.class);
		
		
		bind(ScoringFunctionsForPopulationGraph.class).asEagerSingleton();
		
		
	}

}
