/**
 * 
 */
package ch.ethz.matsim.supernetwork.model;

import java.util.List;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.clustering.cluster.ClusterNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.ClusteringNetworkRegionAlgorithm;
import ch.ethz.matsim.supernetwork.clustering.clustersContainer.KDNode;
import ch.ethz.matsim.supernetwork.clustering.clustersContainer.KDTreeClustersContainer;


/**
 * @author stefanopenazzi
 *
 */
public class ClusteringActivities {

	public ClusteringActivities(Scenario scenario) {
		
		List<ClusterNetworkRegionImpl> regions;
		ClusteringNetworkRegionAlgorithm cn = new ClusteringNetworkRegionAlgorithm(scenario);
		regions = cn.getRegions();
		
		KDNode root = null;
		
		for(ClusterNetworkRegionImpl reg: regions) {
			root = root.Insert(reg);
		}
		
		KDTreeClustersContainer container = new KDTreeClustersContainer(root);
		
		for(Person p: scenario.getPopulation().getPersons().values()) {
			if(p.getPlans().size() >= 1) {
		     for(PlanElement pe: p.getPlans().get(0).getPlanElements()) {
		    	 if ( !(pe instanceof Activity) ) continue;
		    	 KDNode kdn = container.nearestNeighbourSearch(((Activity)pe).getCoord());
		    	 kdn.getCluster().addActivity((Activity)pe);
		     }	
		   }
		}
		
	}
}
