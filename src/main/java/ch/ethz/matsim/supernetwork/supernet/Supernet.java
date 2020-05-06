/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;


/**
 * @author stefanopenazzi
 *
 */
public interface Supernet {

	public List<Middlenetwork> getMiddlenetworks();
	public ClustersContainer<ClusterActivitiesLocation,ElementActivity> getActivitiesClusterContainer();
	
	public void setActivitiesClustersContainer(ClustersContainer<ClusterActivitiesLocation,ElementActivity> cc);
	public void setMiddlenetworks(List<Middlenetwork> hf);
	public void setSupernetworkRoutingModule(SupernetworkRoutingModule supernetworkRoutingModule);
	
	public void treesCalculation();


}
