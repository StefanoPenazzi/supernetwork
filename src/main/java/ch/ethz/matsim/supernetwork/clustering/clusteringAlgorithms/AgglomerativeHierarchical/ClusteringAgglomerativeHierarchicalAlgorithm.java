/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.AgglomerativeHierarchical;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.ClusterDefaultImpl;
import ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.ClusteringAlgorithm;


/**
 * @author stefanopenazzi
 *
 */
public class ClusteringAgglomerativeHierarchicalAlgorithm implements ClusteringAlgorithm {

	private List<ClusterDefaultImpl> clusters = new ArrayList();
	private List<Activity> activitiesBase = null;
	
	public ClusteringAgglomerativeHierarchicalAlgorithm(List<Activity> activitiesBase) {
		this.activitiesBase = activitiesBase;
	}
	
}
