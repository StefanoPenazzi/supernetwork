/**
 * 
 */

package ch.ethz.matsim.supernetwork.routing.network.cluster.elements.superlink;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.supernode.TimeSupernode;

/**
 * @author stefanopenazzi
 *
 */
public interface Superlink {
	
	public TimeSupernode getFrom();
	public TimeSupernode getTo();
	public double getWeight();

}
