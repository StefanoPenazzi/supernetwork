/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import java.util.List;

import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public class NodeImpl implements Node{

	private int id;
	private List<Link> inLinks;
	private List<Link> outLinks;
	
	public NodeImpl(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	};
	public List<Link> getInLinks() {
		return this.inLinks;
	};
	public List<Link> getOutLinks() {
		return this.outLinks;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		
	};
	
}
