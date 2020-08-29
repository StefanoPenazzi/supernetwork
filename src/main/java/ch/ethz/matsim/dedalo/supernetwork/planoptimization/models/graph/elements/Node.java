/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements;

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
	public void addInLink(Link l);
	public void addOutLink(Link l);

}
