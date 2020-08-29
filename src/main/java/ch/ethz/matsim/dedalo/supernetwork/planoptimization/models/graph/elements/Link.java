/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements;

/**
 * @author stefanopenazzi
 *
 */
public interface Link {
	
	public int getId();
	public int getFromNodeId();
	public int getToNodeId();
	public void setFromNodeId(int fromNodeId);
	public void setToNodeId(int toNodeId);

}
