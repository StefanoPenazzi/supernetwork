/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.cluster;

import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public interface Cluster {
	
	List<Activity> getActivities();
	int getId();
	Coord getCentroid();
	void addActivity(Activity act);

}
