/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface MiddlenetworkFactory {

	public Middlenetwork create(Cluster cluster,Subnetwork subnetwork);
}
