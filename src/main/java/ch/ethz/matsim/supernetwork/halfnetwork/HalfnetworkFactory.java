/**
 * 
 */
package ch.ethz.matsim.supernetwork.halfnetwork;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface HalfnetworkFactory {

	public Halfnetwork create(Cluster cluster,Subnetwork subnetwork);
}
