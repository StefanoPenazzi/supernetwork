/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import ch.ethz.matsim.supernetwork.networkmodels.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.networkelements.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.supernetwork.network.SupernetFactory;


/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractSupernetworkExtension extends AbstractModule {

	protected MapBinder<String,ClusteringModelFactory> clusteringModelFactoryBinder;
	protected MapBinder<String,SubnetworkFactory> subnetworkFactoryBinder;
	protected MapBinder<String,MiddlenetworkFactory> middlenetworkFactoryBinder;
	protected MapBinder<String,SupernetFactory> supernetFactoryBinder;
	
	
	@Override
	public void install() {
		
		clusteringModelFactoryBinder = MapBinder.newMapBinder(binder(), String.class,ClusteringModelFactory.class);
		subnetworkFactoryBinder = MapBinder.newMapBinder(binder(), String.class,SubnetworkFactory.class);
		middlenetworkFactoryBinder = MapBinder.newMapBinder(binder(), String.class,MiddlenetworkFactory.class);
		supernetFactoryBinder = MapBinder.newMapBinder(binder(), String.class,SupernetFactory.class);
		
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

	abstract protected void installExtension();
}
