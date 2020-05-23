/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.List;
import java.util.Map;

import org.matsim.core.router.util.TravelTime;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleFactory;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerFactoryImpl implements ContainerManagerFactory {

	private final Map<String, UpdateAlgorithm> updateAlgorithmsMap;
	private final Map<String, SupernetworkRoutesContainer> containersMap;
	private final RoutingManagerFactory routingManagerFactory; 
	private final Map<String, TravelTime> travelTimes;
	
	@Inject
	public ContainerManagerFactoryImpl(RoutingManagerFactory routingManagerFactory,
			Map<String, UpdateAlgorithm> updateAlgorithmsMap,Map<String,
			SupernetworkRoutesContainer> containersMap,Map<String, TravelTime> travelTimes) {
		this.routingManagerFactory = routingManagerFactory;
		this.updateAlgorithmsMap = updateAlgorithmsMap;
		this.containersMap = containersMap;
		this.travelTimes = travelTimes;
	}
	
	@Override
	public ContainerManager createContainerManager(List<Middlenetwork> middlenetworks) {
		
		//check if the maps have the same keys
		
		return new ContainerManagerImpl(this.routingManagerFactory.createRoutingManager(middlenetworks),
				this.updateAlgorithmsMap,this.containersMap,middlenetworks,travelTimes);
	}

}
