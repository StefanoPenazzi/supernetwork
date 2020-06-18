/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import java.util.List;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.core.router.TripStructureUtils.Trip;

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
	private double activityDuration;
	private double activityUtilityFunctionValue;
	private Leg trip;
	
	protected TdspLink(int id,int fromNodeId,int toNodeId,Activity activity,Leg trip,double activityDuration,double activityUtilityFunctionValue) {
		this.id=id;
		this.fromNodeId = fromNodeId;
		this.toNodeId = toNodeId;
		this.activity = activity;
		this.trip = trip;
		this.activityDuration = activityDuration;
		this.activityUtilityFunctionValue = activityUtilityFunctionValue;
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
	
	public Leg getTrip() {
		return this.trip;
	}
	
	public double getActivityDuration() {
		return this.activityDuration;
	} 
	
	public double getActivityUtilityFunctionValue() {
		return this.activityUtilityFunctionValue;
	}
}
