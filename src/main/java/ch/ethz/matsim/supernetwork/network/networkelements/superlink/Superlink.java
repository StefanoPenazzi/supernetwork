/**
 * 
 */

package ch.ethz.matsim.supernetwork.network.networkelements.superlink;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.network.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public interface Superlink {
	
	public Supernode getFrom();
	public Supernode getTo();
	public double getWeight();

}
