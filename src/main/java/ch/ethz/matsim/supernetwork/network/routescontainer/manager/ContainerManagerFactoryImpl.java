/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.Map;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerFactoryImpl implements ContainerManagerFactory {

	private final Map<String, UpdateAlgorithm> updateAlgorithmsMap;
	private final Map<String, SupernetworkRoutesContainer> containersMap;
	private final SupernetworkRoutingModule supernetworkRoutingModule; 
	
	@Inject
	public ContainerManagerFactoryImpl(SupernetworkRoutingModule supernetworkRoutingModule,
			Map<String, UpdateAlgorithm> updateAlgorithmsMap,Map<String,
			SupernetworkRoutesContainer> containersMap) {
		this.supernetworkRoutingModule = supernetworkRoutingModule;
		this.updateAlgorithmsMap = updateAlgorithmsMap;
		this.containersMap = containersMap;
	}
	
	@Override
	public ContainerManager createContainerManager() {
		
		//check if the maps have the same keys
		
		return new ContainerManagerImpl(this.supernetworkRoutingModule,this.updateAlgorithmsMap,this.containersMap);
	}

}
