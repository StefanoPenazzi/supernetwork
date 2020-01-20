/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.halfnetwork.Halfnetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface Supernet {

	public List<Halfnetwork> getHalfnetworks();
	public ClustersContainer getClusterContainer();

}
