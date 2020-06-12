/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import java.util.List;

/**
 * @author stefanopenazzi
 *
 */
public interface Node {
	
	public int getId();
	public void setId(int id);
	public List<Link> getInLinks();
	public List<Link> getOutLinks();

}
