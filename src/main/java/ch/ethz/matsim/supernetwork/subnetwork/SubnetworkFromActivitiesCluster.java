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
	
	public double networkByActivitiesRadius(Cluster<ElementActivity> cluster) {
		double radius =0;
		for(ElementActivity ea: cluster.getComponents()) {
			if(ea.getNextActivity() != null) {
				double dist = Math.pow(cluster.getCentroid().getX()- ea.getNextActivity().getCoord().getX(), 2)+
						Math.pow(cluster.getCentroid().getY()- ea.getNextActivity().getCoord().getY(), 2);
				if(radius<dist) {
					radius = dist;
				}
			}
		}
		return Math.sqrt(radius);
	}

}
