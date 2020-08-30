/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import org.matsim.core.router.util.LeastCostPathCalculatorFactory;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManagerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutesContainerImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerFactoryImpl;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerInitialization;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithmStaticFreqAnalysis;
import ch.ethz.matsim.dedalo.routing.network.cluster.models.supernetwork_model.SupernetworkInitializationEvent;
import ch.ethz.matsim.dedalo.routing.router.cluster.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterRoutingModuleFactoryImpl;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterLeastCostPathCalculatorFactoryImpl;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManagerImpl;

/**
 * @author stefanopenazzi
 *
 */
public class RoutingInitializationModule extends AbstractRoutingExtension {

	@Override
	protected void installExtension() {
		 //network by clustering
		this.addControlerListenerBinding().to(RoutingManagerInitialization.class);
		
		bindActivityManager().to(ActivityManagerImpl.class).asEagerSingleton();

        install(new ClusteringModule());
        install(new SubnetworkModule());
        install(new MiddlenetworkModule());
        
		    //routing manager
		bind(LeastCostPathCalculatorFactory.class).to(ClusterLeastCostPathCalculatorFactoryImpl.class);
		
		bindContainerManager().to(RoutingGeneralManagerImpl.class).asEagerSingleton();
		bindContainerManagerFactory().to(RoutingGeneralManagerFactoryImpl.class);
		
		bindRoutesContainer("car").to(RoutesContainerImpl.class);
		bindUpdateAlgorithm("car").to(UpdateAlgorithmStaticFreqAnalysis.class);
		
		bindSupernetworkRoutingModuleFactory().to(ClusterRoutingModuleFactoryImpl.class);
		bindSupernetworkLeastCostTreeCalculatorFactory().to(FastDijkstraShortestTreeFactory.class);
		
		bindRoutingManagerFactory().to(RoutingManagerFactoryImpl.class);
        
	}
}
