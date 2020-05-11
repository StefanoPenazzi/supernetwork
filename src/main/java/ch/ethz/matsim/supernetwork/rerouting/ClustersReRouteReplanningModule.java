/**
 * 
 */
package ch.ethz.matsim.supernetwork.rerouting;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.population.algorithms.PlanAlgorithm;
import org.matsim.core.replanning.modules.AbstractMultithreadedModule;

import com.google.inject.Provider;


/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteReplanningModule extends AbstractMultithreadedModule{
	
public static final String NAME = "ClustersReRoute";
	
    private final Provider<ClustersReRouteModel> modelProvider;

	public ClustersReRouteReplanningModule(GlobalConfigGroup globalConfigGroup,
			Provider<ClustersReRouteModel> modelProvider) {
		super(globalConfigGroup);
		this.modelProvider = modelProvider;
	}

	@Override
	public PlanAlgorithm getPlanAlgoInstance() {
		ClustersReRouteModel clusterReRouteModel = modelProvider.get();
		return new ClustersReRouteModelAlgorithm(clusterReRouteModel);
	}

}
