/**
 * 
 */
package ch.ethz.matsim.supernetwork.middlelink;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class MiddlelinkImpl implements Middlelink{

	private Supernode from;
	private Node to;
	private double weight;
	
	public MiddlelinkImpl(Supernode from,Node to,double weight) {
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
	public Node getTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
