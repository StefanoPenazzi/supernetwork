/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.List;

import org.matsim.api.core.v01.network.Node;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutingModule {

	public void calcTree(final Node root,List<Node> toNodes ,final double departureTime);

}
