/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.elements.subnetwork;

import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;

/**
 * @author stefanopenazzi
 *
 */
public interface SubnetworkFactory {

	public Subnetwork generateSubnetworkByCluster(Cluster<ElementActivity> cluster);
}
