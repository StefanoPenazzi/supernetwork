/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import javax.inject.Inject;

import org.matsim.api.core.v01.network.Network;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.core.router.util.LeastCostPathCalculatorFactory;
import org.matsim.core.router.util.RoutingNetwork;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class FastDijkstraShortestTreeFactory implements LeastCostPathCalculatorFactory {
	
	private final ArrayRoutingMiddleNetworkFactory routingNetworkFactory;
	private RoutingNetwork routingNetwork;

	@Inject
	public FastDijkstraShortestTreeFactory() {
		this.routingNetworkFactory = new ArrayRoutingMiddleNetworkFactory();
	}

	public synchronized LeastCostPathCalculator createPathCalculator(final Middlenetwork middlenetwork,
			final TravelDisutility travelCosts, final TravelTime travelTimes) {

		routingNetwork = this.routingNetworkFactory.createRoutingMiddleNetwork(middlenetwork);
		FastRouterDelegateFactory fastRouterFactory = new ArrayFastRouterDelegateFactory();

		return new FastDijkstra(routingNetwork, travelCosts, travelTimes, null, fastRouterFactory);
	}

	@Override
	public LeastCostPathCalculator createPathCalculator(Network network, TravelDisutility travelCosts,
			TravelTime travelTimes) {
		return null;
	}

}
