/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Node;

/**
 * @author stefanopenazzi
 *
 */
public class TdspLink implements Link {

	private int id;
	private int fromNodeId;
	private int toNodeId;
	private Activity activity;
	private Leg leg;
	
	protected TdspLink(int id,int fromNodeId,int toNodeId,Activity activity,Leg leg) {
		this.id=id;
		this.fromNodeId = fromNodeId;
		this.toNodeId = toNodeId;
		this.activity = activity;
		this.leg = leg;
	}
	
	@Override
	public int getId() {
		return this.id ;
	};
	
	@Override
	public int getFromNodeId() {
		return this.fromNodeId;
	};
	
	@Override
	public int getToNodeId() {
		return this.toNodeId;
	}

	@Override
	public void setFromNodeId(int fromNodeId) {
		this.fromNodeId = fromNodeId;
	}

	@Override
	public void setToNodeId(int toNodeId) {
		this.toNodeId = toNodeId;
	};
	
	public Activity getActivity() {
		return this.activity;
	}
	
	public Leg getLeg() {
		return this.leg;
	}
}
