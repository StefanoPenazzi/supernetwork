/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.supernetwork;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.population.algorithms.PlanAlgorithm;
import org.matsim.core.replanning.modules.AbstractMultithreadedModule;

import com.google.inject.Provider;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.replanning.rerouting.ClustersReRouteModel;
import ch.ethz.matsim.supernetwork.replanning.rerouting.ClustersReRouteModelAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkReplanningModule extends AbstractMultithreadedModule {

	private final Provider<SupernetworkModel> supernetworkModelProvider;
	
	/**
	 * @param globalConfigGroup
	 */
	public SupernetworkReplanningModule(GlobalConfigGroup globalConfigGroup, Provider<SupernetworkModel> supernetworkModelProvider) {
		super(globalConfigGroup);
		this.supernetworkModelProvider =  supernetworkModelProvider;
	}

	@Override
	public PlanAlgorithm getPlanAlgoInstance() {
		SupernetworkModel supernetworkModel =  this.supernetworkModelProvider.get();
		return new SupernetworkModelAlgorithm(supernetworkModel);
	}

}
