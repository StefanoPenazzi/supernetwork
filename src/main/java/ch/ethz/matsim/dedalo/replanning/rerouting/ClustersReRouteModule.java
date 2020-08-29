/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.rerouting;

import org.matsim.core.controler.AbstractModule;
import org.matsim.core.router.util.LeastCostPathCalculatorFactory;

import ch.ethz.matsim.dedalo.modules.SupernetworkModule;
import ch.ethz.matsim.dedalo.routing.router.SupernetworkLeastCostPathCalculatorFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteModule extends AbstractModule{
	
	public static final String STRATEGY_NAME = "ClustersReRoute";
	
	@Override
	public void install() {
		
		//For a strategy
		//addPlanStrategyBinding(STRATEGY_NAME).toProvider(ClustersReRouteStrategyProvider.class);
		//bind(ClustersReRouteModel.class).to(ClustersReRouteModelImpl.class);
		
		//only the LeastCostPathCalculator
		
		bind(LeastCostPathCalculatorFactory.class).to(SupernetworkLeastCostPathCalculatorFactoryImpl.class);
		
		install(new SupernetworkModule());
	}
}
