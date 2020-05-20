/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworRoutingModuleFactoryImpl;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainerImpl;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.ContainerManagerFactoryImpl;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmImpl;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmStaticFreqAnalysis;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerModule extends AbstractSupernetworkExtension {

	@Override
	public void installExtension() {
		
		bindContainerManagerFactory().to(ContainerManagerFactoryImpl.class );
		
		bindSupernetworkRoutesContainer("car").to(SupernetworkRoutesContainerImpl.class);
		bindUpdateAlgorithm("car").to(UpdateAlgorithmStaticFreqAnalysis.class);
		
		bindSupernetworkRoutingModuleFactory().to(SupernetworRoutingModuleFactoryImpl.class);
		bindSupernetworkLeastCostTreeCalculatorFactory().to(FastDijkstraShortestTreeFactory.class);
		
		//install(new SupernetworkTripRouterModule());
	}

	

}
