/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.NodeData;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetRoutesContainer {
	
	public void add(Id<Node> nodeId, int time,NodeData[] nd);
	public NodeData[] get(Id<Node> nodeId, int time);

}
