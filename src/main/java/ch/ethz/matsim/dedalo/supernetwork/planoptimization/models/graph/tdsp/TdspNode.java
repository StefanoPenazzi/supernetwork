/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdsp;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Node;

/**
 * @author stefanopenazzi
 *
 */
public class TdspNode implements Node {

	private int id;
	private List<Link> inLinks;
	private List<Link> outLinks;
	private Activity activity;
	
	public TdspNode(int id,Activity activity ) {
		this.id = id;
		this.activity = activity;
		inLinks = new ArrayList<>();
		outLinks = new ArrayList<>();
	}
	
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public List<Link> getInLinks() {
		return this.inLinks;
	}

	@Override
	public List<Link> getOutLinks() {
		return this.outLinks;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		
	}

	public Activity getActivity() {
		return this.activity;
	}

	@Override
	public void addInLink(Link l) {
		inLinks.add(l);
		
	}

	@Override
	public void addOutLink(Link l) {
		outLinks.add(l);
		
	}
	
}
