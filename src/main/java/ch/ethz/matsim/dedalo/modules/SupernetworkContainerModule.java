/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import com.google.inject.Singleton;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManagerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutesContainerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithmStaticFreqAnalysis;
import ch.ethz.matsim.dedalo.routing.router.cluster.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterRoutingModuleFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerModule extends AbstractRoutingExtension {

	@Override
	public void installExtension() {
		
		bindContainerManager().to(RoutingGeneralManagerImpl.class).asEagerSingleton();
		bindContainerManagerFactory().to(RoutingGeneralManagerFactoryImpl.class);
		
		bindRoutesContainer("car").to(RoutesContainerImpl.class);
		bindUpdateAlgorithm("car").to(UpdateAlgorithmStaticFreqAnalysis.class);
		
		bindSupernetworkRoutingModuleFactory().to(ClusterRoutingModuleFactoryImpl.class);
		bindSupernetworkLeastCostTreeCalculatorFactory().to(FastDijkstraShortestTreeFactory.class);
		
		bindRoutingManagerFactory().to(RoutingManagerFactoryImpl.class);
		
	}

	

}
