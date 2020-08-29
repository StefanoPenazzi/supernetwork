/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.rerouting;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.population.algorithms.PlanAlgorithm;
import org.matsim.core.replanning.modules.AbstractMultithreadedModule;
import org.matsim.core.router.TripRouter;
import org.matsim.facilities.ActivityFacilities;

import com.google.inject.Provider;


/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteReplanningModule extends AbstractMultithreadedModule{
	
public static final String NAME = "ClustersReRoute";
	
    private final Provider<ClustersReRouteModel> modelProvider;
    private final ActivityFacilities facilities;
    private final TripRouter tripRouter;

	public ClustersReRouteReplanningModule(GlobalConfigGroup globalConfigGroup,
			Provider<ClustersReRouteModel> modelProvider, ActivityFacilities facilities,TripRouter tripRouter) {
		super(globalConfigGroup);
		this.modelProvider = modelProvider;
		this.facilities = facilities;
		this.tripRouter = tripRouter;
	}

	@Override
	public PlanAlgorithm getPlanAlgoInstance() {
		ClustersReRouteModel clusterReRouteModel = modelProvider.get();
		return new ClustersReRouteModelAlgorithm(clusterReRouteModel,this.facilities,this.tripRouter);
	}

}
