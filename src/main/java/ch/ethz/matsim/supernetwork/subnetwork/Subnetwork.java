/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import java.util.List;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;

/**
 * @author stefanopenazzi
 *
 */
public interface Subnetwork {

	public  int getId();
	public  List<Node> getNodes();
	public  List<Link> getLinks();
}