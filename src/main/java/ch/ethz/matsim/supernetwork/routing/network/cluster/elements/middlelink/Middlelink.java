/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlelink;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.supernode.Supernode;


/**
 * @author stefanopenazzi
 *
 */
public interface Middlelink {

	public Supernode getFrom();
	public Node getTo();
	public double getWeight();
	public Link getLink();
	
}
