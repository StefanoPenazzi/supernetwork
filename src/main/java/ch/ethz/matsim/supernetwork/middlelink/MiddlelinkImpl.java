/**
 * 
 */
package ch.ethz.matsim.supernetwork.middlelink;

import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class MiddlelinkImpl implements Middlelink{

	private Supernode from;
	private Supernode to;
	private double weight;
	
	public MiddlelinkImpl(Supernode from,Supernode to,double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	@Override
	public Supernode getFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supernode getTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
