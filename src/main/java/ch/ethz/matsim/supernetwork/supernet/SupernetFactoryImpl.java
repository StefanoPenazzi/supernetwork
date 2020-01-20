/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.kd_tree.KDTreeClustersContainer;
import ch.ethz.matsim.supernetwork.halfnetwork.Halfnetwork;
import ch.ethz.matsim.supernetwork.halfnetwork.HalfnetworkImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetFactoryImpl implements SupernetFactory {

    private final Provider<KDTreeClustersContainer> clustersContainerProvider;
	
	@Inject
	public SupernetFactoryImpl(Provider<KDTreeClustersContainer> clustersContainerProvider) {
		this.clustersContainerProvider = clustersContainerProvider;
	}

	@Override
	public Supernet create(List<Halfnetwork> halfnetworks) {
		return new SupernetImpl(clustersContainerProvider.get(),halfnetworks);
	}
}
