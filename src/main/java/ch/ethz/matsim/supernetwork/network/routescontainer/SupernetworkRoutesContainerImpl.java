/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer;

import java.util.List;
import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.core.router.util.NodeData;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.PredecessorNode;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleFactory;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.PathTimeKey;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmOutput;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkRoutesContainerImpl implements SupernetworkRoutesContainer{

	private TreeMap<PathTimeKey,Path> container = new TreeMap<>();
	
	@Inject
	public SupernetworkRoutesContainerImpl() {
		
	}
	
	@Override
	public void add(Supernode supernode, Node toNode, double time,Path ln) {
		container.put(new PathTimeKey(supernode,time,toNode), ln);
	}
	
	@Override
	public Path getPath(Supernode supernode, Node toNode, double time) {
		PathTimeKey d = new PathTimeKey(supernode,time,toNode);
		Path p = container.floorEntry(d).getValue();
		return p;
	}  
	
	@Override
	public boolean empty() {
		if (container.size() == 0)
			return true;
		else 
			return false;
	}
}
