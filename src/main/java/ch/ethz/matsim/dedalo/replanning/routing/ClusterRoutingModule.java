/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.routing;

import com.google.inject.Singleton;
import ch.ethz.matsim.dedalo.modules.AbstractSupernetworkExtension;
import ch.ethz.matsim.dedalo.modules.RoutingInitializationModule;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerUpdate;

/**
 * @author stefanopenazzi
 *
 */
public class ClusterRoutingModule extends AbstractSupernetworkExtension{
	
	public static final String STRATEGY_NAME = "ClustersReRoute";
	
	@Override
	public void installExtension() {
		
		//only the LeastCostPathCalculator
		
		  //initialization
		install(new RoutingInitializationModule());
		
		  //update during the simulation
		bind(RoutingManagerUpdate.class).in(Singleton.class);
		this.addControlerListenerBinding().to(RoutingManagerUpdate.class);
		
	}
}
