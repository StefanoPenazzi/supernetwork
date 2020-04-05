/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

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
	
	public NodeData[] calcLeastCostTree(final Node fromNode, final double startTime);

}
