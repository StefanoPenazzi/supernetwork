/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.AgglomerativeHierarchical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;
import ch.ethz.matsim.supernetwork.clustering.element.ElementActivity;
import ch.ethz.matsim.supernetwork.clustering.cluster.CALDefaultImpl;

/**
 * @author stefanopenazzi
 * 
 * https://github.com/haifengl/smile/tree/master/core/src/main/java/smile/clustering
 *
 */
public class HierarchicalClusteringActivities {

	private List<ElementActivity> activities;
	private List<Cluster<ElementActivity>> clusters;
	
	public HierarchicalClusteringActivities(List<ElementActivity> activities,double height) {
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
			double iX = activities.get(i).getActivity().getCoord().getX();
			double iY = activities.get(i).getActivity().getCoord().getY();
			for(int j=0;j<activities.size();++j) {
				pm[i][j] = Math.sqrt(Math.pow(iX - activities.get(j).getActivity().getCoord().getX(),2)+Math.pow(iY - activities.get(j).getActivity().getCoord().getY(),2));
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
	    	 clusters.add(new CALDefaultImpl(0));
	     }
		 
	     for(int i =0;i<part.length;++i) {
	    	 activities.get(i).setCluster(clusters.get(part[i]));
	    	 clusters.get(part[i]).addComponent(activities.get(i));
	    
	     }
	}
	
	public List<Cluster<ElementActivity>> getClusters(){
		return this.clusters;
	}
	
}
