/**
 * 
 */
package ch.ethz.matsim.supernetwork.superlink;

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
