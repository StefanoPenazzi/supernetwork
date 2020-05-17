/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkelements.superlink;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class SuperlinkImpl implements Superlink  {
	
	
	private Supernode from;
	private Supernode to;
	private double weight;
	
	public SuperlinkImpl(Supernode from,Supernode to,double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	@Override
	public Supernode getFrom() {
		// TODO Auto-generated method stub
		return this.from;
	}

	@Override
	public Supernode getTo() {
		// TODO Auto-generated method stub
		return this.to;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return this.weight;
	}

}
