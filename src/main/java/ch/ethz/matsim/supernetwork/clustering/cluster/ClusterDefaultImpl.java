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
public class ClusterDefaultImpl implements Cluster {
	
	private final int id;
	private List<Activity> activities  = new ArrayList();
	private Coord centroid;
	
	ClusterDefaultImpl(int id,List<Activity> activities,Coord centroid){
		this.id = id;
		this.activities = activities;
		this.centroid = centroid;
	}
	
	public List<Activity> getActivities(){
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
	public void addActivity(Activity act) {
		activities.add(act);
	}
	public void removeActivity(Activity act) {
		activities.remove(act);
	}

}
