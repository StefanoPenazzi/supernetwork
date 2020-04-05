/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworRoutingModuleFactoryImpl;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleFactory;

/**
 * @author stefanopenazzi
 *
 */

	
public class SupernetworkTripRouterModule extends AbstractModule {

	@Override
	public void install() {
		
		install(new SupernetworkLeastCostTreeCalculatorModule());
		bind(SupernetworkRoutingModuleFactory.class).to(SupernetworRoutingModuleFactoryImpl.class);
	     
	}
    
}


