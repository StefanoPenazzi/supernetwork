/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

/**
 * @author stefanopenazzi
 *
 */
public class Link {

	private int id;
	private Node startNode;
	private Node endNode;
	private String mode;
	
	protected Link() {
		
	}
	
	public int getId() {
		return this.id ;
	};
	public Node getStartNode() {
		return this.startNode;
	};
	public Node getEndNode() {
		return this.endNode;
	};
}
