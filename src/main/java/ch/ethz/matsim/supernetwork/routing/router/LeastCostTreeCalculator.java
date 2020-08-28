/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.router;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.core.router.util.NodeData;
import org.matsim.vehicles.Vehicle;

/**
 * @author stefanopenazzi
 *
 */
public interface LeastCostTreeCalculator extends LeastCostPathCalculator {
	
	public Path[] calcLeastCostTree(final Node fromNode, final List<Node> toNodes ,final double startTime);
	public Path calcLeastCostPathFromPredecessorNode(final Node toNode, double startTime ,PredecessorNode[] pn);
}
