/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import org.matsim.core.controler.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;

import ch.ethz.matsim.dedalo.routing.manager.ContainerManager;
import ch.ethz.matsim.dedalo.routing.manager.ContainerManagerFactory;
import ch.ethz.matsim.dedalo.routing.manager.RoutesContainer;
import ch.ethz.matsim.dedalo.routing.manager.RoutingManagerFactory;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.dedalo.routing.network.cluster.models.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.dedalo.routing.router.SupernetworkLeastCostTreeCalculatorFactory;
import ch.ethz.matsim.dedalo.routing.router.SupernetworkRoutingModuleFactory;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.containers.PlansForPopulationContainer;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlanManagerFactory;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlansForPopulationManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManager;


/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractSupernetworkExtension extends AbstractModule {
	
	@Override
	public void install() {
		installExtension();
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
