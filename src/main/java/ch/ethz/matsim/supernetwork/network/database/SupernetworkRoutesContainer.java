/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutesContainer {
	
	public void add(Supernode supernode,  Node toNode ,double time,Path ln);
	public Path getPath(Supernode supernode, Node toNode ,double time);
	public boolean empty();
}
