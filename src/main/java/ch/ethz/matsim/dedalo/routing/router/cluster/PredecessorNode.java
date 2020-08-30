/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router.cluster;

import org.matsim.api.core.v01.network.Link;

/**
 * @author stefanopenazzi
 *
 */
public class PredecessorNode {
	
	private final double time;
	private final double cost;
	private final Link link;
	private final int predecessor;
	
	public PredecessorNode(int predecessor, double time, double cost, Link link) {
		this.predecessor = predecessor;
		this.time = time;
		this.cost = cost;
		this.link = link;
	}
	
	public double getTime() {
		return this.time;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public Link getLink() {
		return this.link;
	}
	
	public int getPredecessor() {
		return this.predecessor;
	}

}
