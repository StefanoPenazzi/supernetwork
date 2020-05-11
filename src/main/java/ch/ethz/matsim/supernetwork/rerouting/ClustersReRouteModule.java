/**
 * 
 */
package ch.ethz.matsim.supernetwork.rerouting;

import org.matsim.core.controler.AbstractModule;


/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteModule extends AbstractModule{
	
	public static final String STRATEGY_NAME = "ClustersReRoute";
	
	@Override
	public void install() {
		addPlanStrategyBinding(STRATEGY_NAME).toProvider(ClustersReRouteStrategyProvider.class);
		
		bind(ClustersReRouteModel.class).to(ClustersReRouteModelImpl.class);
	}

}
