/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkmodels.clustering_models;

import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;

/**
 * @author stefanopenazzi
 *
 */
public interface ClusteringModelFactory {

	public ClustersContainer generateClusteringModel();
}
