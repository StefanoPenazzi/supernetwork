/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public class Node {

	private int id;
	private Activity activity;
	private Link[] inLinks;
	private Link[] outLinks;
	
	protected Node() {
		
	}
	
	public int getId() {
		return this.id;
	};
	public Activity getActivity() {
		return this.activity;
	};
	public Link[] getInLinks() {
		return this.inLinks;
	};
	public Link[] getOutLinks() {
		return this.outLinks;
	};
}
