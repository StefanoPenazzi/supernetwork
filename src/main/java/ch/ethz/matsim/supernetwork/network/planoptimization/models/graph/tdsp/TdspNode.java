/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import java.util.List;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Node;

/**
 * @author stefanopenazzi
 *
 */
public class TdspNode implements Node {

	private int id;
	private List<Link> inLinks;
	private List<Link> outLinks;
	
	public TdspNode(int id) {
		this.id = id;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Link> getInLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Link> getOutLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		
	}

	
}
