/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clustersContainer;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;

/**
 * @author stefanopenazzi
 *
 */
public interface ClustersContainer {

	void addCluster();
	void deleteCluster();
	void merge2Cluster(Cluster c1, Cluster c2);
	Cluster[] splitCluster(Cluster c);
	Cluster getCluster(Coord c);
	Cluster getCluster(int id);
	Cluster getCluster(Activity act);
}
