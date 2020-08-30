/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import org.matsim.core.controler.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManager;
import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManagerFactory;
import ch.ethz.matsim.dedalo.routing.manager.RoutesContainer;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerFactory;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.dedalo.routing.network.cluster.models.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterLeastCostTreeCalculatorFactory;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterRoutingModuleFactory;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManager;

/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractRoutingExtension extends AbstractModule {

	protected MapBinder<String,ClusteringModelFactory> clusteringModelFactoryBinder;
	protected MapBinder<String,SubnetworkFactory> subnetworkFactoryBinder;
	protected MapBinder<String,MiddlenetworkFactory> middlenetworkFactoryBinder;
	protected MapBinder<String, UpdateAlgorithm> updateAlgorithmBinder;
	protected MapBinder<String, RoutesContainer> containerBinder;
	
	
	@Override
	public void install() {
		
		clusteringModelFactoryBinder = MapBinder.newMapBinder(binder(), String.class,ClusteringModelFactory.class);
		subnetworkFactoryBinder = MapBinder.newMapBinder(binder(), String.class,SubnetworkFactory.class);
		middlenetworkFactoryBinder = MapBinder.newMapBinder(binder(), String.class,MiddlenetworkFactory.class);
		updateAlgorithmBinder = MapBinder.newMapBinder(binder(), String.class,UpdateAlgorithm.class);
		containerBinder = MapBinder.newMapBinder(binder(), String.class,RoutesContainer.class);
		
		installExtension();
	}
	
	protected final LinkedBindingBuilder<ClusteringModelFactory> bindClusteringModelFactory(String name) {
		return clusteringModelFactoryBinder.addBinding(name);
	}
	protected final LinkedBindingBuilder<SubnetworkFactory> bindSubnetworkFactory(String name) {
		return subnetworkFactoryBinder.addBinding(name);
	}
	protected final LinkedBindingBuilder<MiddlenetworkFactory> bindMiddlenetworkFactory(String name) {
		return middlenetworkFactoryBinder.addBinding(name);
	}
	protected final LinkedBindingBuilder<UpdateAlgorithm> bindUpdateAlgorithm(String mode) {
		return updateAlgorithmBinder.addBinding(mode);
	}
	protected final LinkedBindingBuilder<RoutesContainer> bindRoutesContainer(String mode) {
		return containerBinder.addBinding(mode);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<ClusterLeastCostTreeCalculatorFactory> bindSupernetworkLeastCostTreeCalculatorFactory() {
		return bind(ClusterLeastCostTreeCalculatorFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<ClusterRoutingModuleFactory> bindSupernetworkRoutingModuleFactory() {
		return bind(ClusterRoutingModuleFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<RoutingGeneralManager> bindContainerManager() {
		return bind(RoutingGeneralManager.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<RoutingGeneralManagerFactory> bindContainerManagerFactory() {
		return bind(RoutingGeneralManagerFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<RoutingManagerFactory> bindRoutingManagerFactory() {
		return bind(RoutingManagerFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<ActivityManager > bindActivityManager () {
		return bind(ActivityManager.class);
	}

	abstract protected void installExtension();
}