/**
 * 
 */
package ch.ethz.matsim.supernetwork.middlelink;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.NetworkUtils;

import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class MiddlelinkImpl implements Middlelink{

	private Supernode from;
	private Node to;
	private double weight;
	private static int id = 0;
	private Link link;
	
	public MiddlelinkImpl(Supernode from,Node to,double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		link = NetworkUtils.createLink(Id.create("middlelink"+ Integer.toString(id) , Link.class),from.getNode(),to,null,0,0,0,0);
		id++;
		
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

	@Override
	public Link getLink() {
		return link;
	}

}
