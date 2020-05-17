/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkelements.subnetwork;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;

/**
 * @author stefanopenazzi
 *
 */
public interface SubnetworkFactory {

	public Subnetwork generateSubnetworkByCluster(Cluster<ElementActivity> cluster);
}
