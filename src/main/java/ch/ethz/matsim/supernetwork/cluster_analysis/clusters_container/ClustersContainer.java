/**
 * 
 */
package ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;



/**
 * @author stefanopenazzi
 *
 */
public interface ClustersContainer {

	void addCluster(Cluster c);
	void deleteCluster(Cluster c);
	void merge2Cluster(Cluster c1, Cluster c2);
	Cluster[] splitCluster(Cluster c);
	Cluster getCluster(Coord c);
	Cluster getCluster(int id);
	Cluster getCluster(Activity act);
}
