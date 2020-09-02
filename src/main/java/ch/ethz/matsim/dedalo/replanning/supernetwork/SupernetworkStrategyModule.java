/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.core.controler.AbstractModule;
import com.google.inject.Provides;
import ch.ethz.matsim.dedalo.modules.PlanOptimizationModule;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlansForPopulationManager;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkStrategyModule extends AbstractModule{
	
	public static final String STRATEGY_NAME = "Supernetwork";
	
	@Override
	public void install() {
		//For a strategy
		addPlanStrategyBinding(STRATEGY_NAME).toProvider(SupernetworkStrategyProvider.class);
		
		install(new PlanOptimizationModule());
		
	}
	
	@Provides
	public SupernetworkStrategyModel provideSupernetworkModel(PlansForPopulationManager plansForPopulationManager) {
		return new SupernetworkStrategyModelImpl(plansForPopulationManager);
	}
}
