/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import ch.ethz.matsim.supernetwork.networkmodels.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.containers.PlansForPopulationContainer;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanManagerFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlansForPopulationManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.supernetwork.network.utilities.ActivityManager;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkLeastCostTreeCalculatorFactory;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleFactory;
import ch.ethz.matsim.supernetwork.network.database.containers.RoutesContainer;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManagerFactory;
import ch.ethz.matsim.supernetwork.network.database.manager.RoutingManagerFactory;
import ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms.UpdateAlgorithm;


/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractSupernetworkExtension extends AbstractModule {

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
	
	protected final com.google.inject.binder.LinkedBindingBuilder<SupernetworkLeastCostTreeCalculatorFactory> bindSupernetworkLeastCostTreeCalculatorFactory() {
		return bind(SupernetworkLeastCostTreeCalculatorFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<SupernetworkRoutingModuleFactory> bindSupernetworkRoutingModuleFactory() {
		return bind(SupernetworkRoutingModuleFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<ContainerManager> bindContainerManager() {
		return bind(ContainerManager.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<ContainerManagerFactory> bindContainerManagerFactory() {
		return bind(ContainerManagerFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<RoutingManagerFactory> bindRoutingManagerFactory() {
		return bind(RoutingManagerFactory.class);
	}
	
	//plan optimizazion 
	
	protected final com.google.inject.binder.LinkedBindingBuilder<PlansForPopulationContainer> bindPlansForPopulationContainer() {
		return bind(PlansForPopulationContainer.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<PlansForPopulationManager> bindPlansForPopulationManager() {
		return bind(PlansForPopulationManager.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<PlanModelFactory> bindPlanModelFactory() {
		return bind(PlanModelFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<PlanManagerFactory> bindPlanManagerFactory() {
		return bind(PlanManagerFactory.class);
	}
	
	protected final com.google.inject.binder.LinkedBindingBuilder<ActivityManager > bindActivityManager () {
		return bind(ActivityManager.class);
	}

	abstract protected void installExtension();
}
