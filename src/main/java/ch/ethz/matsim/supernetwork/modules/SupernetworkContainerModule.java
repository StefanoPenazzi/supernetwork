/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import com.google.inject.Singleton;

import ch.ethz.matsim.supernetwork.routing.router.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.supernetwork.routing.router.SupernetworRoutingModuleFactoryImpl;
import ch.ethz.matsim.supernetwork.routing.manager.RoutesContainerImpl;
import ch.ethz.matsim.supernetwork.routing.manager.ContainerManagerFactoryImpl;
import ch.ethz.matsim.supernetwork.routing.manager.ContainerManagerImpl;
import ch.ethz.matsim.supernetwork.routing.manager.RoutingManagerFactoryImpl;
import ch.ethz.matsim.supernetwork.routing.manager.updatealgorithms.UpdateAlgorithmStaticFreqAnalysis;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerModule extends AbstractSupernetworkExtension {

	@Override
	public void installExtension() {
		
		bindContainerManager().to(ContainerManagerImpl.class).asEagerSingleton();
		bindContainerManagerFactory().to(ContainerManagerFactoryImpl.class);
		
		bindRoutesContainer("car").to(RoutesContainerImpl.class);
		bindUpdateAlgorithm("car").to(UpdateAlgorithmStaticFreqAnalysis.class);
		
		bindSupernetworkRoutingModuleFactory().to(SupernetworRoutingModuleFactoryImpl.class);
		bindSupernetworkLeastCostTreeCalculatorFactory().to(FastDijkstraShortestTreeFactory.class);
		
		bindRoutingManagerFactory().to(RoutingManagerFactoryImpl.class);
		
		//install(new SupernetworkTripRouterModule());
	}

	

}
