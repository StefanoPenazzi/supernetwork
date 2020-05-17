/**
 * 
 */
package ch.ethz.matsim.supernetwork.network;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;


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
	
	public Supernode getSupernodeFromActivity(Activity act);
	public Path getPathFromRoutesContainer(Activity act,Node toNode,int time);


}
