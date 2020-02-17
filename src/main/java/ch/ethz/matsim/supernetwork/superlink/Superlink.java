/**
 * 
 */
package ch.ethz.matsim.supernetwork.superlink;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public interface Superlink {
	
	public Supernode getFrom();
	public Supernode getTo();
	public double getWeight();

}
