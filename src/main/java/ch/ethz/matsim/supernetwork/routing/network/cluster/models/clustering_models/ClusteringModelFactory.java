/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.models.clustering_models;

import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.clusters_container.ClustersContainer;

/**
 * @author stefanopenazzi
 *
 */
public interface ClusteringModelFactory {

	public ClustersContainer generateClusteringModel();
}
