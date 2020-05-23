/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworRoutingModuleFactoryImpl;
import ch.ethz.matsim.supernetwork.network.database.containers.RoutesContainerImpl;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManagerFactoryImpl;
import ch.ethz.matsim.supernetwork.network.database.manager.RoutingManagerFactoryImpl;
import ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms.UpdateAlgorithmStaticFreqAnalysis;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerModule extends AbstractSupernetworkExtension {

	@Override
	public void installExtension() {
		
		bindContainerManagerFactory().to(ContainerManagerFactoryImpl.class );
		
		bindRoutesContainer("car").to(RoutesContainerImpl.class);
		bindUpdateAlgorithm("car").to(UpdateAlgorithmStaticFreqAnalysis.class);
		
		bindSupernetworkRoutingModuleFactory().to(SupernetworRoutingModuleFactoryImpl.class);
		bindSupernetworkLeastCostTreeCalculatorFactory().to(FastDijkstraShortestTreeFactory.class);
		
		bindRoutingManagerFactory().to(RoutingManagerFactoryImpl.class);
		
		//install(new SupernetworkTripRouterModule());
	}

	

}
