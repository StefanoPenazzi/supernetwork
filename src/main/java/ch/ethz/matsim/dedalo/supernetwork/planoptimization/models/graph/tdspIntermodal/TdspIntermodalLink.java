/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal;

import java.util.List;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.router.TripStructureUtils.Trip;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Node;

/**
 * @author stefanopenazzi
 *
 */
public class TdspIntermodalLink implements Link {

	private int id;
	private int fromNodeId;
	private int toNodeId;
	private String type;
	private String mode;
	private double duration;
	private double utility;
	
	protected TdspIntermodalLink(int id,int fromNodeId,int toNodeId, String type, String mode,double duration, double utility) {
		this.id=id;
		this.fromNodeId = fromNodeId;
		this.toNodeId = toNodeId;
		this.utility = utility;
		this.mode = mode;
		this.type = type;
		this.duration = duration;
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
	
	public String getType() {
		return this.type;
	}
	
	public double getDuration() {
		return this.duration;
	}
	
	public double getUtility() {
		return this.utility;
	}
	
	public String getMode() {
		return this.mode;
	}
	
}
