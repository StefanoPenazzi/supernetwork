/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;

/**
 * @author stefanopenazzi
 *
 */
public interface Graph extends PlanModel {

	public Node[] getNodes();
	public Link[] getLinks();
	public void buildLinksNotIntoNodes();
	public void buildLinksIntoNodes();
	public void addNode(Node node);
	public void addLink(Link link);
}
