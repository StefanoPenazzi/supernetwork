/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.core.router.util.NodeData;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.PredecessorNode;
import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetRoutesContainer {
	
	public void add(Supernode supernode,  Node toNode ,int time,Path ln);
	public Path getPath(Supernode supernode, Node toNode ,int time);

}
