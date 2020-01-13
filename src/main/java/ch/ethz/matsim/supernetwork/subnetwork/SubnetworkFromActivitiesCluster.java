/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;
import ch.ethz.matsim.supernetwork.clustering.element.ElementActivity;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkFromActivitiesCluster {
	
	public SubnetworkFromActivitiesCluster() {
		
	}
	
	public Subnetwork fromActivitiesLocations(Cluster<ElementActivity> cluster) {
		Subnetwork sn = null;
		List<Activity> activitiesInSubNetwork = new ArrayList();
		
		for(ElementActivity ea: cluster.getComponents()) {
			
		}
		
		return sn;
	}

}
