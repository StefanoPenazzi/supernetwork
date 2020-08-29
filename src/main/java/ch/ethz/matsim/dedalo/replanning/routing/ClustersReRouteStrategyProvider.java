/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.routing;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.replanning.PlanStrategy;
import org.matsim.core.replanning.PlanStrategyImpl;
import org.matsim.core.replanning.selectors.RandomPlanSelector;
import org.matsim.core.router.TripRouter;
import org.matsim.facilities.ActivityFacilities;
import com.google.inject.Inject;
import com.google.inject.Provider;


/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteStrategyProvider implements Provider<PlanStrategy> {

	private final GlobalConfigGroup globalConfigGroup;
	private final Provider<ClustersReRouteModel> clusterReRouteModelProvider;
	private final ActivityFacilities facilities;
	private final TripRouter tripRouter;
	
	@Inject
	ClustersReRouteStrategyProvider(GlobalConfigGroup globalConfigGroup,Provider<ClustersReRouteModel> clusterReRouteModelProvider,
			ActivityFacilities facilities,TripRouter tripRouter){
		this.globalConfigGroup = globalConfigGroup;
		this.clusterReRouteModelProvider = clusterReRouteModelProvider;
		this.facilities = facilities;
		this.tripRouter = tripRouter;
	}
	
	@Override
	public PlanStrategy get() {
		
		PlanStrategyImpl.Builder builder = new PlanStrategyImpl.Builder(new RandomPlanSelector<>());
		builder.addStrategyModule(new ClustersReRouteReplanningModule(globalConfigGroup,clusterReRouteModelProvider,facilities,tripRouter));
		return builder.build();
	}

}
