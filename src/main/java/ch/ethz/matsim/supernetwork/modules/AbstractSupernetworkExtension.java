/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;

import com.google.inject.Singleton;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;

import ch.ethz.matsim.supernetwork.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.models.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.supernetwork.supernet.SupernetFactory;
import ch.ethz.matsim.supernetwork.supernet.SupernetImpl;

/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractSupernetworkExtension extends AbstractModule {

	protected MapBinder<String,ClusteringModelFactory> clusteringModelFactoryBinder;
	protected MapBinder<String,SubnetworkFactory> subnetworkFactoryBinder;
	protected MapBinder<String,MiddlenetworkFactory> middlenetworkFactoryBinder;
	protected MapBinder<String,SupernetFactory> supernetFactoryBinder;
	protected MapBinder<String,TrafficDataContainer> trafficDataContainerBinder;
	
	@Override
	public void install() {
		
		clusteringModelFactoryBinder = MapBinder.newMapBinder(binder(), String.class,ClusteringModelFactory.class);
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
	protected final LinkedBindingBuilder<SupernetFactory> bindSupernetFactory(String name) {
		return supernetFactoryBinder.addBinding(name);
	}
	protected final LinkedBindingBuilder<TrafficDataContainer> bindTrafficDataContainer(String name) {
		return trafficDataContainerBinder.addBinding(name);
	}

	abstract protected void installExtension();
}
