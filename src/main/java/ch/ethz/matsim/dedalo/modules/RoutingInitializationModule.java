/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import org.matsim.core.router.util.LeastCostPathCalculatorFactory;

import ch.ethz.matsim.dedalo.routing.manager.ContainerManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.ContainerManagerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutesContainerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerInitialization;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithmStaticFreqAnalysis;
import ch.ethz.matsim.dedalo.routing.network.cluster.models.supernetwork_model.SupernetworkInitializationEvent;
import ch.ethz.matsim.dedalo.routing.router.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.dedalo.routing.router.SupernetworRoutingModuleFactoryImpl;
import ch.ethz.matsim.dedalo.routing.router.SupernetworkLeastCostPathCalculatorFactoryImpl;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManagerImpl;

/**
 * @author stefanopenazzi
 *
 */
public class RoutingInitializationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		 //network by clustering
		this.addControlerListenerBinding().to(RoutingManagerInitialization.class);
		
		bindActivityManager().to(ActivityManagerImpl.class).asEagerSingleton();

        install(new ClusteringModule());
        install(new SubnetworkModule());
        install(new MiddlenetworkModule());
        
		    //routing manager
		bind(LeastCostPathCalculatorFactory.class).to(SupernetworkLeastCostPathCalculatorFactoryImpl.class);
		
		bindContainerManager().to(ContainerManagerImpl.class).asEagerSingleton();
		bindContainerManagerFactory().to(ContainerManagerFactoryImpl.class);
		
		bindRoutesContainer("car").to(RoutesContainerImpl.class);
		bindUpdateAlgorithm("car").to(UpdateAlgorithmStaticFreqAnalysis.class);
		
		bindSupernetworkRoutingModuleFactory().to(SupernetworRoutingModuleFactoryImpl.class);
		bindSupernetworkLeastCostTreeCalculatorFactory().to(FastDijkstraShortestTreeFactory.class);
		
		bindRoutingManagerFactory().to(RoutingManagerFactoryImpl.class);
        
	}
}
