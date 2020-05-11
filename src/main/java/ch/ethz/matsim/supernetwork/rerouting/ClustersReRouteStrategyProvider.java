/**
 * 
 */
package ch.ethz.matsim.supernetwork.rerouting;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.replanning.PlanStrategy;
import org.matsim.core.replanning.PlanStrategyImpl;
import org.matsim.core.replanning.modules.TripsToLegsModule;
import org.matsim.core.replanning.selectors.RandomPlanSelector;
import org.matsim.core.router.TripRouter;
import org.matsim.facilities.ActivityFacilities;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.ethz.matsim.supernetwork.supernet.Supernet;

/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteStrategyProvider implements Provider<PlanStrategy> {

	private final GlobalConfigGroup globalConfigGroup;
	private final Provider<ClustersReRouteModel> clusterReRouteModelProvider;
	
	@Inject
	ClustersReRouteStrategyProvider(GlobalConfigGroup globalConfigGroup,Provider<ClustersReRouteModel> clusterReRouteModelProvider){
		this.globalConfigGroup = globalConfigGroup;
		this.clusterReRouteModelProvider = clusterReRouteModelProvider;
	}
	
	@Override
	public PlanStrategy get() {
		
		PlanStrategyImpl.Builder builder = new PlanStrategyImpl.Builder(new RandomPlanSelector<>());
		builder.addStrategyModule(new ClustersReRouteReplanningModule(globalConfigGroup,clusterReRouteModelProvider));
		return builder.build();
	}

}
