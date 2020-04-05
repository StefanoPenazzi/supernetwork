/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetImpl implements Supernet{

	private  List<Middlenetwork> middlenetworks;
	private  ClustersContainer<ClusterActivitiesLocation,ElementActivity> clusterContainer;
	private  SupernetworkRoutingModule supernetworkRoutingModule;
	
	
	@Inject
	public SupernetImpl() {
		
	}
	
	@Override
	public List<Middlenetwork> getMiddlenetworks() {
		
		return this.middlenetworks;
	}

	@Override
	public void setActivitiesClustersContainer(ClustersContainer<ClusterActivitiesLocation,ElementActivity> cc) {
		this.clusterContainer = cc ;
	}

	@Override
	public void setMiddlenetworks(List<Middlenetwork> hf) {
		this.middlenetworks = hf;
	}

	@Override
	public ClustersContainer<ClusterActivitiesLocation,ElementActivity> getActivitiesClusterContainer() {
		
		return this.clusterContainer;
	}

	@Override
	public void setSupernetworkRoutingModule(SupernetworkRoutingModule supernetworkRoutingModule) {
		this.supernetworkRoutingModule = supernetworkRoutingModule;
		
	}

	@Override
	public void treesCalculation() {
		for (Middlenetwork mn: middlenetworks) {
			this.supernetworkRoutingModule.calcTree(mn.getSuperNode().getNode(), 10);
		}
		
	}

	

}
