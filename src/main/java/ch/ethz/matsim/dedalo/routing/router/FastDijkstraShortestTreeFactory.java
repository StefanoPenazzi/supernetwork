/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.router.costcalculators.TravelDisutilityFactory;
import org.matsim.core.router.util.RoutingNetwork;
import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;


/**
 * @author stefanopenazzi
 *
 */
public class FastDijkstraShortestTreeFactory implements  SupernetworkLeastCostTreeCalculatorFactory {
	private final ArrayRoutingMiddleNetworkFactory routingNetworkFactory;
	private RoutingNetwork routingNetwork;
	private boolean routingNetworkSettled = false;
	private Map<String, TravelDisutilityFactory> travelDisutilityFactories;
	private Map<String, TravelTime> travelTimes;
	private Scenario scenario;
	

	@Inject
	public FastDijkstraShortestTreeFactory(Map<String, TravelDisutilityFactory> travelDisutilityFactories,Map<String, TravelTime> travelTimes, Scenario scenario) {
		this.routingNetworkFactory = new ArrayRoutingMiddleNetworkFactory();
		this.travelDisutilityFactories = travelDisutilityFactories;
		this.travelTimes = travelTimes;
		this.scenario = scenario;
	}
	
	

	@Override
	public LeastCostTreeCalculator createTreeCalculator(String routingMode) {
		
			//routingNetwork = this.routingNetworkFactory.createRoutingMiddleNetwork(network,middlenetworks);
		if(this.routingNetworkSettled) {
			FastRouterDelegateFactory fastRouterFactory = new ArrayFastRouterDelegateFactory();
			// the travel time & disutility refer to the routing mode:
			TravelDisutilityFactory travelDisutilityFactory = this.travelDisutilityFactories.get(routingMode);
			if (travelDisutilityFactory == null) {
				throw new RuntimeException("No TravelDisutilityFactory bound for mode "+routingMode+".");
			}
			TravelTime travelTime = travelTimes.get(routingMode);
			if (travelTime == null) {
				throw new RuntimeException("No TravelTime bound for mode "+routingMode+".");
			}
			
			//the purpose of this person is only for using calcLeastCostPath in dijkstra. for the shortest tree is not necessary
			Person person =  scenario.getPopulation().getPersons().values().iterator().next();

			return new SupernetworkFastDijkstra(routingNetwork, travelDisutilityFactory.createTravelDisutility(travelTime), travelTime, null, fastRouterFactory,person);
		}
		else {
			throw new java.lang.RuntimeException("routingNetwork not initialized in FastDijkstraShortestTreeFactory");
		}
		
	}

	@Override
	public void setRoutingNetwork(Network network, List<Middlenetwork> middlenetworks) {
		routingNetwork = this.routingNetworkFactory.createRoutingMiddleNetwork(network,middlenetworks);
		this.routingNetworkSettled = true;
	}
}
