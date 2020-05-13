/**
 * 
 */
package ch.ethz.matsim.supernetwork.rerouting;

import org.matsim.core.controler.AbstractModule;
import org.matsim.core.router.FastAStarLandmarksFactory;
import org.matsim.core.router.util.LeastCostPathCalculatorFactory;


/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteModule extends AbstractModule{
	
	public static final String STRATEGY_NAME = "ClustersReRoute";
	
	@Override
	public void install() {
		
		//For a strategy
		addPlanStrategyBinding(STRATEGY_NAME).toProvider(ClustersReRouteStrategyProvider.class);
		bind(ClustersReRouteModel.class).to(ClustersReRouteModelImpl.class);
		
		//Just as a LeastCostPathCalculator
		//bind(LeastCostPathCalculatorFactory.class).to(ClustersReRoutePathCalculatorFactory.class);
	}

}
