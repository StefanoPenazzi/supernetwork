/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork;

import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface MiddlenetworkFactory {

	public Middlenetwork create(Cluster cluster,Subnetwork subnetwork);
}
