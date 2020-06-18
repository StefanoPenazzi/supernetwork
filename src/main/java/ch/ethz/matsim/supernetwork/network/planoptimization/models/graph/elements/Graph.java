/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import java.util.List;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;

/**
 * @author stefanopenazzi
 *
 */
public interface Graph extends PlanModel {

	public Node[] getNodes();
	public Link[] getLinks();
	public void buildLinksNotIntoNodes(List<? extends Node> nodesList, List<? extends Link> linksList);
	public void buildLinksIntoNodes(List<? extends Node> nodesList);
}
