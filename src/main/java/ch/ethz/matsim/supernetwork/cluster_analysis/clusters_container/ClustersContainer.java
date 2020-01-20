/**
 * 
 */
package ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.Element;



/**
 * @author stefanopenazzi
 *
 */
public interface ClustersContainer<T extends Cluster,S extends Element> {

	void addCluster(T c);
	void deleteCluster(T c);
	void merge2Cluster(T c1, T c2);
	T[] splitCluster(T c);
	T getCluster(Coord c);
	T getCluster(int id);
	T getCluster(S element);
	
}
