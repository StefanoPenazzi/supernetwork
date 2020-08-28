/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.models.clustering_models;

import java.util.ArrayList;
import java.util.List;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.router.StageActivityTypes;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.router.TripStructureUtils.Trip;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.modules.Config.RegionHierarchicalCSConfigGroup;
import ch.ethz.matsim.supernetwork.network.utilities.ActivityManager;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.CALNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.CALRegion;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.clusters_container.kd_tree.KDNode;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.clusters_container.kd_tree.KDTreeClustersContainer;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.models.centroid.ClusteringNetworkRegionAlgorithm;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.models.connectivity.AgglomerativeHierarchical.HierarchicalClusteringActivities;

/**
 * @author stefanopenazzi
 *
 */
public class RegionHierarchicalCS {

	public static ClustersContainer<ClusterActivitiesLocation,ElementActivity> generateClustersContainer(
			Scenario scenario,int cut, TripRouter tripRouter,
			ActivityFacilities facilities,ActivityManager activityManager) {
	
		List<CALNetworkRegionImpl> regions;
		ClusteringNetworkRegionAlgorithm cn = new ClusteringNetworkRegionAlgorithm(scenario);
		regions = cn.getRegions();
		
		KDTreeClustersContainer container = new KDTreeClustersContainer(null,regions.size());
		
		for(CALNetworkRegionImpl reg: regions) {
			container.add(reg);
		}
		
		for(Person p: scenario.getPopulation().getPersons().values()) {
			if(p.getPlans().size() >= 1) {
				
			 List<Trip> trips = TripStructureUtils.getTrips(p.getPlans().get(0), tripRouter.getStageActivityTypes());
			 
		     for(Trip tp: trips) {
		    	 
		    	 KDNode kdn = container.nearestNeighbourSearch(tp.getOriginActivity().getCoord());
		    	 ElementActivity ea = new ElementActivity(FacilitiesUtils.toFacility(tp.getOriginActivity(),facilities),
		    			 FacilitiesUtils.toFacility(tp.getDestinationActivity(),facilities),p,kdn.getCluster(),tp.getOriginActivity().getStartTime());
		    	 kdn.getCluster().addComponent(ea);
		    	 
		     }	
		   }
		}
		
		List<CALRegion> clusters = new ArrayList();
		
		for(CALNetworkRegionImpl cnr: regions) {
			if(cnr.getComponents().size() > 1) {
				List<ElementActivity> act = cnr.getComponents();
				//the value used for the cut of the clusters tree is related to the type of linkage used
				HierarchicalClusteringActivities hca = new HierarchicalClusteringActivities(act,cut);
				//add clusters
				for(Cluster<ElementActivity> c:hca.getClusters()) {
					c.computeCentroid();
					CALRegion cc = (CALRegion)c;
					cc.setRegionNodes(cnr.getNodes());
					clusters.add(cc);
				}
			}
		}
		
		KDTreeClustersContainer container1 = new KDTreeClustersContainer(null,clusters.size());
		for(CALRegion c: clusters) {
			container1.add(c);
		}
		return container1;
	}
	
	public static class Factory implements ClusteringModelFactory{

		private final RegionHierarchicalCSConfigGroup regionHierarchicalCSConfigGroup;
		private final Scenario scenario;
		private final TripRouter tripRouter;
		private final ActivityFacilities facilities;
		private final ActivityManager activityManager;
		
		@Inject 
		public Factory(Scenario scenario,RegionHierarchicalCSConfigGroup regionHierarchicalCSConfigGroup,
				TripRouter tripRouter,ActivityFacilities facilities,ActivityManager activityManager) {
			this.scenario = scenario;
			this.regionHierarchicalCSConfigGroup = regionHierarchicalCSConfigGroup;
			this.tripRouter = tripRouter;
			this.facilities = facilities;
			this.activityManager = activityManager;
		}
		
		@Override
		public ClustersContainer generateClusteringModel() {
			return generateClustersContainer(scenario,regionHierarchicalCSConfigGroup.getCut(),tripRouter, 
					this.facilities,this.activityManager);
		}
	}
}
