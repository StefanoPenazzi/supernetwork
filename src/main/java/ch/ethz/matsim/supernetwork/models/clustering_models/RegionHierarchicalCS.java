/**
 * 
 */
package ch.ethz.matsim.supernetwork.models.clustering_models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.CALDefaultImpl;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.CALNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.kd_tree.KDNode;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.kd_tree.KDTreeClustersContainer;
import ch.ethz.matsim.supernetwork.cluster_analysis.models.centroid.ClusteringNetworkRegionAlgorithm;
import ch.ethz.matsim.supernetwork.cluster_analysis.models.connectivity.AgglomerativeHierarchical.HierarchicalClusteringActivities;
import ch.ethz.matsim.supernetwork.modules.Config.RegionHierarchicalCSConfigGroup;

/**
 * @author stefanopenazzi
 *
 */
public class RegionHierarchicalCS {

	public static ClustersContainer<ClusterActivitiesLocation,ElementActivity> generateClustersContainer(Scenario scenario,int cut) {
		// TODO Auto-generated method stub
		List<CALNetworkRegionImpl> regions;
		ClusteringNetworkRegionAlgorithm cn = new ClusteringNetworkRegionAlgorithm(scenario);
		regions = cn.getRegions();
		
		KDTreeClustersContainer container = new KDTreeClustersContainer(null,regions.size());
		for(CALNetworkRegionImpl reg: regions) {
			container.add(reg);
		}
		
		for(Person p: scenario.getPopulation().getPersons().values()) {
			if(p.getPlans().size() >= 1) {
			 List<PlanElement> le = p.getPlans().get(0).getPlanElements();
		     for(PlanElement pe: le) {
		    	 if (!(pe instanceof Activity)) continue;
		    	 KDNode kdn = container.nearestNeighbourSearch(((Activity)pe).getCoord());
		    	 ElementActivity ea = new ElementActivity((Activity)pe,p,kdn.getCluster());
		    	 kdn.getCluster().addComponent(ea);
		     }	
		   }
		}
		
		List<CALDefaultImpl> clusters = new ArrayList();
		
		for(CALNetworkRegionImpl cnr: regions) {
			if(cnr.getComponents().size() > 1) {
				List<ElementActivity> act = cnr.getComponents();
				//the value used for the cut of the clusters tree is related to the type of linkage used
				HierarchicalClusteringActivities hca = new HierarchicalClusteringActivities(act,cut);
				//add clusters
				for(Cluster<ElementActivity> c:hca.getClusters()) {
					c.computeCentroid();
					clusters.add((CALDefaultImpl)c);
				}
			}
		}
		
		KDTreeClustersContainer container1 = new KDTreeClustersContainer(null,clusters.size());
		for(CALDefaultImpl c: clusters) {
			container1.add(c);
		}
		
		//set the centroid for each new cluster in clusters
		//for(Cluster cdi: clusters) {
		//	cdi.computeCentroid();
		//}
		
		//subnetwork generation
		//List<SubnetworkDefaultImpl> subnetworks = new ArrayList();
		//for(Cluster cdi: clusters) {
		//	subnetworks.add((SubnetworkDefaultImpl) SubnetworkFromActivitiesCluster.fromActivitiesLocations(scenario.getNetwork(), cdi,0.9));
		//}
		
		System.out.println();
		return container1;
	}
	
	public static class Factory implements ClusteringModelFactory{

		RegionHierarchicalCSConfigGroup regionHierarchicalCSConfigGroup;
		Scenario scenario;
		
		@Inject 
		public Factory(Scenario scenario,RegionHierarchicalCSConfigGroup regionHierarchicalCSConfigGroup) {
			this.scenario = scenario;
			this.regionHierarchicalCSConfigGroup = regionHierarchicalCSConfigGroup;
		}
		
		@Override
		public ClustersContainer generateClusteringModel() {
			return generateClustersContainer(scenario,regionHierarchicalCSConfigGroup.getCut());
		}
	}
}
