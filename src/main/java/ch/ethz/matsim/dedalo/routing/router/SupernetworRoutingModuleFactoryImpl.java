/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.algorithms.TransportModeNetworkFilter;
import org.matsim.core.router.SingleModeNetworksCache;
import org.matsim.core.router.costcalculators.TravelDisutilityFactory;
import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.dedalo.routing.router.SupernetworkRoutingModuleFactory;

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
	
	@Inject
	public SupernetworRoutingModuleFactoryImpl(Map<String, TravelTime> travelTimes,
	 Map<String, TravelDisutilityFactory> travelDisutilityFactories,
	 SingleModeNetworksCache singleModeNetworksCache,
	 Network network,
	 PopulationFactory populationFactory,
	 SupernetworkLeastCostTreeCalculatorFactory supernetworkLeastCostTreeCalculatorFactory,
	 Scenario scenario) {
		
		this.travelTimes = travelTimes;
		this.travelDisutilityFactories =travelDisutilityFactories;
		this.singleModeNetworksCache=singleModeNetworksCache;
		this.network=network;
		this.populationFactory=populationFactory;
		this.supernetworkLeastCostTreeCalculatorFactory=supernetworkLeastCostTreeCalculatorFactory;
		this.scenario= scenario;
		this.routingMode = null;
		
	}

	@Override
	public SupernetworkRoutingModule getSupernetworkRoutingModule(String mode,List<Middlenetwork> middlenetworks) {
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

		this.supernetworkLeastCostTreeCalculatorFactory.setRoutingNetwork(filteredNetwork, middlenetworks); 
		
		return new SupernetworkRoutingModuleImpl(routingMode, this.supernetworkLeastCostTreeCalculatorFactory);
		
	}
}
