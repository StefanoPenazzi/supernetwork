/**
 * 
 */
package ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container;

import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.Element;



/**
 * @author stefanopenazzi
 *
 */
public interface ClustersContainer<T extends Cluster,S extends Element> {

	public void addCluster(T c);
	public void deleteCluster(T c);
	public void merge2Cluster(T c1, T c2);
	public T[] splitCluster(T c);
	public T getCluster(Coord c);
	public T getCluster(int id);
	public T getCluster(S element);
	public List<T> getClusters();
	
}
