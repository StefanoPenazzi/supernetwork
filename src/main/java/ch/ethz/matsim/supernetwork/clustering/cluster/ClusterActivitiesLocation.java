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
public abstract class ClusterActivitiesLocation implements Cluster<Activity>{
	
	private final int id;
	private List<Activity> activities  = new ArrayList();
	private Coord centroid;
	
	public ClusterActivitiesLocation(int id,List<Activity> activities,Coord centroid){
		this.id = id;
		this.activities = activities;
		this.centroid = centroid;
	}
	
	public ClusterActivitiesLocation(int id){
		this.id = id;
	}
	
	public List<Activity> getComponents(){
		return Collections.unmodifiableList(activities);
	}
	public int getId() {
		return this.id;
	}
	public Coord getCentroid() {
		return this.centroid;
	}
	public void setCentroid(Coord centroid) {
		this.centroid = centroid;
	}
	public void addComponent(Activity act) {
		activities.add(act);
	}
	public void removeComponent(Activity act) {
		activities.remove(act);
	}
	public void computeCentroid() {
		double x = 0;
		double y = 0;
		for(Activity act:activities) {
			x += act.getCoord().getX();
			y += act.getCoord().getY();
		}
		x = x/activities.size();
		y = y/activities.size();
		this.centroid = new Coord(x,y);
	}
	
	public double variance() {
		double var = 0;
		for(Activity act:activities) {
			var += Math.sqrt(Math.pow(act.getCoord().getX() - centroid.getX(), 2) + Math.pow(act.getCoord().getY() - centroid.getY(), 2));
		}
		var = var/activities.size();
		return var;
	}
	
	public double maxDistAct() {
		double max = 0;
		for(Activity act:activities) {
			double dist = Math.sqrt(Math.pow(act.getCoord().getX() - centroid.getX(), 2) + Math.pow(act.getCoord().getY() - centroid.getY(), 2));
			if(max < dist) {
				max = dist;
			}
		}
		return max;
	}
	
	public double minDistAct() {
		double min = Double.MAX_VALUE;
		for(Activity act:activities) {
			double dist = Math.sqrt(Math.pow(act.getCoord().getX() - centroid.getX(), 2) + Math.pow(act.getCoord().getY() - centroid.getY(), 2));
			if(min > dist) {
				min = dist;
			}
		}
		return min;
	}
}
