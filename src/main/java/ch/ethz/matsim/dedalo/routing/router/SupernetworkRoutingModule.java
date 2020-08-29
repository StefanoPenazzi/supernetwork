/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.core.router.util.NodeData;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutingModule {

	public Path[] calcTree(final Node root,List<Node> toNodes ,final double departureTime);
	//public Path calcPathFromTree(final Node toNode,final double startTime ,final PredecessorNode[] pn);

}
