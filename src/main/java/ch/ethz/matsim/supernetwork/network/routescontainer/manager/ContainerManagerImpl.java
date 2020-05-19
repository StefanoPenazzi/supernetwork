/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmOutput;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerImpl implements ContainerManager {
	
	private final Map<String, UpdateAlgorithm> updateAlgorithmsMap;
	private final Map<String, SupernetworkRoutesContainer> containersMap;
	private final SupernetworkRoutingModule supernetworkRoutingModule; 
	
	public ContainerManagerImpl(SupernetworkRoutingModule supernetworkRoutingModule,
			Map<String, UpdateAlgorithm> updateAlgorithmsMap,Map<String,
			SupernetworkRoutesContainer> containersMap) {
		this.supernetworkRoutingModule = supernetworkRoutingModule;
		this.updateAlgorithmsMap = updateAlgorithmsMap;
		this.containersMap = containersMap;
	}

	@Override
	public void updateContainer(String mode) {
		 
	  SupernetworkRoutesContainer src = containersMap.get(mode);
	  UpdateAlgorithm ua  = updateAlgorithmsMap.get(mode);
	  List<UpdateAlgorithmOutput> inputs = ua.getUpdate(src);
	  for (UpdateAlgorithmOutput mn: inputs) {
	  
		  Path [] paths= this.supernetworkRoutingModule.calcTree(mn.getSupernode().getNode(), mn.getToNodes(),mn.getTime());
		  for(Path p: paths) {
			  src.add(mn.getSupernode(),Iterables.getLast(p.nodes),mn.getTime(),p);
		  }
	  }	
	}
	
	public Path getPath(Supernode supernode, Node toNode ,double time,String mode) {
		return containersMap.get(mode).getPath(supernode, toNode, time);
	}
}
