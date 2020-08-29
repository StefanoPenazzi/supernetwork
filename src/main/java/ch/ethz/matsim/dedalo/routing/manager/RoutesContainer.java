/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutesContainer {
	
	public void add(Node fromNode,  Node toNode ,double time,Path ln);
	public Path getPath(Node fromNode, Node toNode ,double time);
	public boolean empty();
}
