/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.halfnetwork.Halfnetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface Supernet {

	public List<Halfnetwork> getHalfnetworks();
	public ClustersContainer<ClusterActivitiesLocation,ElementActivity> getActivitiesClusterContainer();
	
	public void setActivitiesClustersContainer(ClustersContainer<ClusterActivitiesLocation,ElementActivity> cc);
	public void setHalfnetworks(List<Halfnetwork> hf);

}
