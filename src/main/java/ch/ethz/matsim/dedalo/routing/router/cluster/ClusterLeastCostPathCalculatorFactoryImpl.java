/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router.cluster;

import org.matsim.api.core.v01.network.Network;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.core.router.util.LeastCostPathCalculatorFactory;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;
import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManager;

/**
 * @author stefanopenazzi
 *
 */
public class ClusterLeastCostPathCalculatorFactoryImpl implements LeastCostPathCalculatorFactory{

	private final RoutingGeneralManager conatinerManager;
	
	@Inject
	ClusterLeastCostPathCalculatorFactoryImpl(RoutingGeneralManager routingGeneralManager){
		this.conatinerManager = routingGeneralManager;
	}
	
	@Override
	public LeastCostPathCalculator createPathCalculator(Network network, TravelDisutility travelCosts,
			TravelTime travelTimes) {
		
		return new ClusterLeastCostPathCalculator(conatinerManager);
	}

}
