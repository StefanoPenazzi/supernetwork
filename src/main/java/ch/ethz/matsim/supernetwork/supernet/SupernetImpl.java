/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetImpl implements Supernet{

	private  List<Middlenetwork> halfnetworks;
	private  ClustersContainer<ClusterActivitiesLocation,ElementActivity> clusterContainer;
	
	
	@Inject
	public SupernetImpl() {
		
	}
	
	@Override
	public List<Middlenetwork> getHalfnetworks() {
		
		return this.halfnetworks;
	}

	@Override
	public void setActivitiesClustersContainer(ClustersContainer<ClusterActivitiesLocation,ElementActivity> cc) {
		this.clusterContainer = cc ;
	}

	@Override
	public void setHalfnetworks(List<Middlenetwork> hf) {
		this.halfnetworks = hf;
	}

	@Override
	public ClustersContainer<ClusterActivitiesLocation,ElementActivity> getActivitiesClusterContainer() {
		
		return this.clusterContainer;
	}

	

}