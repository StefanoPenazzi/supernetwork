/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.List;

import javax.inject.Inject;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.core.router.util.RoutingNetwork;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;


/**
 * @author stefanopenazzi
 *
 */
public class FastDijkstraShortestTreeFactory implements  SupernetworkLeastCostTreeCalculatorFactory {
	private final ArrayRoutingMiddleNetworkFactory routingNetworkFactory;
	private RoutingNetwork routingNetwork;

	public FastDijkstraShortestTreeFactory() {
		this.routingNetworkFactory = new ArrayRoutingMiddleNetworkFactory();
	}

	@Override
	public LeastCostTreeCalculator createTreeCalculator(Network network, List<Middlenetwork> middlenetworks,
			TravelDisutility travelCosts, TravelTime travelTimes, Person person) {
		//considering preprocessing?
				routingNetwork = this.routingNetworkFactory.createRoutingMiddleNetwork(network,middlenetworks);
				FastRouterDelegateFactory fastRouterFactory = new ArrayFastRouterDelegateFactory();

				return new SupernetworkFastDijkstra(routingNetwork, travelCosts, travelTimes, null, fastRouterFactory,person);
		
	}
}
