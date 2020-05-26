/**
 * 
 */

package ch.ethz.matsim.supernetwork.network.networkelements.superlink;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.network.networkelements.supernode.Supernode;
import ch.ethz.matsim.supernetwork.network.networkelements.supernode.TimeSupernode;

/**
 * @author stefanopenazzi
 *
 */
public interface Superlink {
	
	public TimeSupernode getFrom();
	public TimeSupernode getTo();
	public double getWeight();

}
