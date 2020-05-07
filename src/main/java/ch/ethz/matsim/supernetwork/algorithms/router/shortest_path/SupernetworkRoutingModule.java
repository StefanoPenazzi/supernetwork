/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.NodeData;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutingModule {

	public NodeData[] calcTree(final Node root,List<Node> toNodes ,final double departureTime);

}
