/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import com.google.inject.Singleton;

import ch.ethz.matsim.dedalo.routing.manager.ContainerManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.ContainerManagerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutesContainerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithmStaticFreqAnalysis;
import ch.ethz.matsim.dedalo.routing.router.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.dedalo.routing.router.SupernetworRoutingModuleFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerModule extends AbstractRoutingExtension {

	@Override
	public void installExtension() {
		
		bindContainerManager().to(ContainerManagerImpl.class).asEagerSingleton();
		bindContainerManagerFactory().to(ContainerManagerFactoryImpl.class);
		
		bindRoutesContainer("car").to(RoutesContainerImpl.class);
		bindUpdateAlgorithm("car").to(UpdateAlgorithmStaticFreqAnalysis.class);
		
		bindSupernetworkRoutingModuleFactory().to(SupernetworRoutingModuleFactoryImpl.class);
		bindSupernetworkLeastCostTreeCalculatorFactory().to(FastDijkstraShortestTreeFactory.class);
		
		bindRoutingManagerFactory().to(RoutingManagerFactoryImpl.class);
		
	}

	

}
