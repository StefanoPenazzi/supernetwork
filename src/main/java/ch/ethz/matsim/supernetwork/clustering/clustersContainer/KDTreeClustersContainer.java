/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clustersContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;

/**
 * @author stefanopenazzi
 *
 */
public class KDTreeClustersContainer implements ClustersContainer {

	private KDNode root = null;
    private List<Cluster> clusters = new ArrayList();
    private KDNode checkedNodes[];
    private KDNode nearestNeighbour;
    private int checkedNodesCounter;
    private int nBoundary;
    private double xMin, xMax;
    private double yMin, yMax;
    private double dMin;
    private boolean xMaxBoundary, xMinBoundary;
    private boolean yMaxBoundary, yMinBoundary;
	
	
    
    public void addCluster(Cluster c) {
		
	}
	public void deleteCluster(Cluster c) {
		
	}
	public void merge2Cluster(Cluster c1, Cluster c2) {
		
	}
	public Cluster[] splitCluster(Cluster c) {
		Cluster[] result = new Cluster[2];
		return result;
	}
	public Cluster getCluster(Coord c) {
		Cluster result = null;
		return result;
	}
	public Cluster getCluster(int id) {
		Cluster result = null;
		return result;
	}
	public Cluster getCluster(Activity act) {
		Cluster result = null;
		return result;
	}
	public List<Cluster> getClusters(){
		return Collections.unmodifiableList(clusters);
	}
}
