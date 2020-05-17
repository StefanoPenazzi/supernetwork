/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.algorithms.TransportModeNetworkFilter;
import org.matsim.core.router.SingleModeNetworksCache;
import org.matsim.core.router.costcalculators.TravelDisutilityFactory;
import org.matsim.core.router.util.TravelTime;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.LeastCostTreeCalculator;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleFactory;
import ch.ethz.matsim.supernetwork.network.Supernet;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworRoutingModuleFactoryImpl implements SupernetworkRoutingModuleFactory {

	private String routingMode;
	
	Map<String, TravelTime> travelTimes;
	Map<String, TravelDisutilityFactory> travelDisutilityFactories;
	SingleModeNetworksCache singleModeNetworksCache;
	Network network;
	PopulationFactory populationFactory;
	SupernetworkLeastCostTreeCalculatorFactory supernetworkLeastCostTreeCalculatorFactory;
	Scenario scenario ;
	Supernet supernet;
	
	@Inject
	public SupernetworRoutingModuleFactoryImpl(Map<String, TravelTime> travelTimes,
	 Map<String, TravelDisutilityFactory> travelDisutilityFactories,
	 SingleModeNetworksCache singleModeNetworksCache,
	 Network network,
	 PopulationFactory populationFactory,
	 SupernetworkLeastCostTreeCalculatorFactory supernetworkLeastCostTreeCalculatorFactory,
	 Scenario scenario,
	 Supernet supernet) {
		
		this.travelTimes = travelTimes;
		this.travelDisutilityFactories =travelDisutilityFactories;
		this.singleModeNetworksCache=singleModeNetworksCache;
		this.network=network;
		this.populationFactory=populationFactory;
		this.supernetworkLeastCostTreeCalculatorFactory=supernetworkLeastCostTreeCalculatorFactory;
		this.scenario= scenario;
		this.supernet=supernet;
		this.routingMode = null;
		
	}

	@Override
	public SupernetworkRoutingModule getSupernetworkRoutingModule(String mode) {
		this.routingMode = mode ;
		
		// the network refers to the (transport)mode:
		Network filteredNetwork = null;

		// Ensure this is not performed concurrently by multiple threads!
		synchronized (this.singleModeNetworksCache.getSingleModeNetworksCache()) {
			filteredNetwork = this.singleModeNetworksCache.getSingleModeNetworksCache().get(routingMode);
			if (filteredNetwork == null) {
				TransportModeNetworkFilter filter = new TransportModeNetworkFilter(network);
				Set<String> modes = new HashSet<>();
				modes.add(routingMode);
				filteredNetwork = NetworkUtils.createNetwork();
				filter.filter(filteredNetwork, modes);
				this.singleModeNetworksCache.getSingleModeNetworksCache().put(routingMode, filteredNetwork);
			}
		}

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
		Person person = scenario.getPopulation().getPersons().values().iterator().next();
		
		LeastCostTreeCalculator routeAlgo =
				supernetworkLeastCostTreeCalculatorFactory.createTreeCalculator(
						filteredNetwork,
						supernet.getMiddlenetworks(),
						travelDisutilityFactory.createTravelDisutility(travelTime),
						travelTime,
						person);

		
		return new SupernetworkRoutingModuleImpl(routingMode, populationFactory, filteredNetwork, routeAlgo);
		
	}
}
