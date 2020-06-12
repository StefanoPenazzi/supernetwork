/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

/**
 * @author stefanopenazzi
 *
 */
public interface Graph {

	public Node[] getNodes();
	public Link[] getLinks();
	public void build();
	public void addNode(Node node);
	public void addLink(Link link);
}
