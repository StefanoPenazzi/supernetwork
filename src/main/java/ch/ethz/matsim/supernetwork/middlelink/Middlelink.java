/**
 * 
 */
package ch.ethz.matsim.supernetwork.middlelink;

import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public interface Middlelink {

	public Supernode getFrom();
	public Supernode getTo();
	public double getWeight();
	
}
