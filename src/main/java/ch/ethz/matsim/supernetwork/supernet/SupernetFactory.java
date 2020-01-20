/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.halfnetwork.Halfnetwork;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetFactory {
	public Supernet create(List<Halfnetwork> halfnetworks);

}
