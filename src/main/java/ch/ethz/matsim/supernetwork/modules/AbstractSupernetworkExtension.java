/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;
import org.matsim.core.router.util.LeastCostPathCalculatorFactory;

import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import ch.ethz.matsim.supernetwork.networkmodels.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.networkelements.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkLeastCostTreeCalculatorFactory;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleFactory;
import ch.ethz.matsim.supernetwork.network.SupernetworkFactory;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.ContainerManagerFactory;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.RoutingManagerFactory;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;


/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractSupernetworkExtension extends AbstractModule {

	protected MapBinder<String,ClusteringModelFactory> clusteringModelFactoryBinder;
	protected MapBinder<String,SubnetworkFactory> subnetworkFactoryBinder;
	protected MapBinder<String,MiddlenetworkFactory> middlenetworkFactoryBinder;
	protected MapBinder<String,SupernetworkFactory> supernetworkFactoryBinder;
	
	protected MapBinder<String, UpdateAlgorithm> updateAlgorithmBinder;
	protected MapBinder<String, SupernetworkRoutesContainer> containerBinder;
	
	
	@Override
	public void install() {
		
		clusteringModelFactoryBinder = MapBinder.newMapBinder(binder(), String.class,ClusteringModelFactory.class);
		subnetworkFactoryBinder = MapBinder.newMapBinder(binder(), String.class,SubnetworkFactory.class);
		middlenetworkFactoryBinder = MapBinder.newMapBinder(binder(), String.class,MiddlenetworkFactory.class);
		supernetworkFactoryBinder = MapBinder.newMapBinder(binder(), String.class,SupernetworkFactory.class);
		
		updateAlgorithmBinder = MapBinder.newMapBinder(binder(), String.class,UpdateAlgorithm.class);
		containerBinder = MapBinder.newMapBinder(binder(), String.class,SupernetworkRoutesContainer.class);
		
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
	protected final LinkedBindingBuilder<SupernetworkFactory> bindSupernetworkFactory(String name) {
		return supernetworkFactoryBinder.addBinding(name);
	}
	
	protected final LinkedBindingBuilder<UpdateAlgorithm> bindUpdateAlgorithm(String mode) {
		return updateAlgorithmBinder.addBinding(mode);
	}
	protected final LinkedBindingBuilder<SupernetworkRoutesContainer> bindSupernetworkRoutesContainer(String mode) {
		return containerBinder.addBinding(mode);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<SupernetworkLeastCostTreeCalculatorFactory> bindSupernetworkLeastCostTreeCalculatorFactory() {
		return bind(SupernetworkLeastCostTreeCalculatorFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<SupernetworkRoutingModuleFactory> bindSupernetworkRoutingModuleFactory() {
		return bind(SupernetworkRoutingModuleFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<ContainerManagerFactory> bindContainerManagerFactory() {
		return bind(ContainerManagerFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<RoutingManagerFactory> bindRoutingManagerFactory() {
		return bind(RoutingManagerFactory.class);
	}

	abstract protected void installExtension();
}
