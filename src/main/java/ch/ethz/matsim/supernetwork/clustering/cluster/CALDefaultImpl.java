/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public class CALDefaultImpl extends ClusterActivitiesLocation {
	
	private double networkRadius = 0;
	private List<Double> networkRadiusArray = new ArrayList(); 
	
	public CALDefaultImpl(int id,List<Activity> activities,Coord centroid){
		super(id,activities,centroid);
	}
	public CALDefaultImpl(int id){
		super(id);
	}
	
	public double getNetworkRadius() {
		return this.networkRadius;
	}
	public void setNetworkRadius(double radius) {
		this.networkRadius = radius;
	}
	public List<Double> getNetworkRadiusArray(){
		return Collections.unmodifiableList(this.networkRadiusArray);
	}
	public void addRadius(double r) {
		this.networkRadiusArray.add(r);
	}

}
