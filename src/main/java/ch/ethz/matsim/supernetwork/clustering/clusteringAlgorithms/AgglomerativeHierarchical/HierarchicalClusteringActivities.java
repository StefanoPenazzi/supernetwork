/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.AgglomerativeHierarchical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;
import ch.ethz.matsim.supernetwork.clustering.cluster.ClusterDefaultImpl;

/**
 * @author stefanopenazzi
 *
 */
public class HierarchicalClusteringActivities {

	private List<Activity> activities;
	private List<Cluster> clusters;
	
	public HierarchicalClusteringActivities(List<Activity> activities,double height) {
		this.activities = activities;
		clusters = new ArrayList();
		run(height);
	}
	
	private void run(double height){
		double[][] pm = proximityActivitiesMatrix();
		WardLinkage wl = new WardLinkage(pm);
		ClusteringAgglomerativeHierarchicalAlgorithm ca = new ClusteringAgglomerativeHierarchicalAlgorithm(null,null);
		ClusteringAgglomerativeHierarchicalAlgorithm res = ca.fit(wl);
		clustersGenerator(res,height);
		//System.out.println("...");
	}
	
	private double[][] proximityActivitiesMatrix(){
		double[][] pm = new double[activities.size()][activities.size()];
		for(int i=0;i<activities.size();++i) {
			double iX = activities.get(i).getCoord().getX();
			double iY = activities.get(i).getCoord().getY();
			for(int j=0;j<activities.size();++j) {
				pm[i][j] = Math.sqrt(Math.pow(iX - activities.get(j).getCoord().getX(),2)+Math.pow(iY - activities.get(j).getCoord().getY(),2));
			}
		}
		return pm;
	}
	
	
	private void clustersGenerator(ClusteringAgglomerativeHierarchicalAlgorithm res,double height) {
	     int[] part = res.partition(height);
	     int currentCluster = -1;
	     int max = 0;
	     for(int i =0;i<part.length;++i) {
	    	 if(max<part[i]) {
	    		 max= part[i];
	    	 }
	     }
	     
	     for(int j=0;j<max+1;++j) {
	    	 clusters.add(new ClusterDefaultImpl(0));
	     }
		 
	     for(int i =0;i<part.length;++i) {
	    	 clusters.get(part[i]).addActivity(activities.get(i));
	    
	     }
	}
	
}
