/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.AgglomerativeHierarchical;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.CALDefaultImpl;
import ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.ClusteringAlgorithm;


/**
 * @author stefanopenazzi
 *
 */
public class ClusteringAgglomerativeHierarchicalAlgorithm implements ClusteringAlgorithm {

	private List<CALDefaultImpl> clusters = new ArrayList();
	private List<Activity> activitiesBase = null;
	
	public ClusteringAgglomerativeHierarchicalAlgorithm(List<Activity> activitiesBase) {
		this.activitiesBase = activitiesBase;
	}
	
	private static final long serialVersionUID = 2L;

    /**
     * An n-1 by 2 matrix of which row i describes the merging of clusters at
     * step i of the clustering. If an element j in the row is less than n, then
     * observation j was merged at this stage. If j &ge; n then the merge
     * was with the cluster formed at the (earlier) stage j-n of the algorithm.
     */
    private int[][] merge;
    /**
     * A set of n-1 non-decreasing real values, which are the clustering height,
     * i.e., the value of the criterion associated with the clustering method
     * for the particular agglomeration.
     */
    private double[] height;

    /**
     * Constructor.
     * @param tree an n-1 by 2 matrix of which row i describes the merging
     *             of clusters at step i of the clustering.
     * @param height the clustering height.
     */
    public ClusteringAgglomerativeHierarchicalAlgorithm(int[][] tree, double[] height) {
        this.merge = tree;
        this.height = height;
    }

    /**
     * Fits the Agglomerative Hierarchical Clustering with given linkage
     * method, which includes proximity matrix.
     * @param linkage a linkage method to merge clusters. The linkage object
     * includes the proximity matrix of data.
     */
    public static ClusteringAgglomerativeHierarchicalAlgorithm fit(Linkage linkage) {
        int n = linkage.size();

        int[][] merge = new int[n - 1][2];
        int[] id = new int[n];
        double[] height = new double[n - 1];

        int[] points = new int[n];
        for (int i = 0; i < n; i++) {
            points[i] = i;
            id[i] = i;
        }

        //this keeps an MST used to find the new cluster based on the linkage
        //Moreover implements efficient methods to update the structure (remove/add clusters)
        FastPair fp = new FastPair(points, linkage);
        
        //clustering algo
        for (int i = 0; i < n - 1; i++) {
        	//save the distance between the two clusters merged in this iteration
        	//this is useful in the tree cuts. 
        	//merge[i]={cluster1id,cluster2id} contains the id of the two clusters merged
            height[i] = fp.getNearestPair(merge[i]);
            linkage.merge(merge[i][0], merge[i][1]);     // merge clusters into one
            fp.remove(merge[i][1]);           // drop b
            fp.updatePoint(merge[i][0]);      // and tell closest pairs about merger

            int p = merge[i][0];
            int q = merge[i][1];
            merge[i][0] = Math.min(id[p], id[q]);
            merge[i][1] = Math.max(id[p], id[q]);
            id[p] = n + i;
        }

        if (linkage instanceof WardLinkage) {
            for (int i = 0; i < height.length; i++) {
                height[i] = Math.sqrt(height[i]);
            }
        }

        return new ClusteringAgglomerativeHierarchicalAlgorithm(merge, height);
    }

    /**
     * Returns an n-1 by 2 matrix of which row i describes the merging of clusters at
     * step i of the clustering. If an element j in the row is less than n, then
     * observation j was merged at this stage. If j &ge; n then the merge
     * was with the cluster formed at the (earlier) stage j-n of the algorithm.
     */
    public int[][] getTree() {
        return merge;
    }

    /**
     * Returns a set of n-1 non-decreasing real values, which are the clustering height,
     * i.e., the value of the criterion associated with the clustering method
     * for the particular agglomeration.
     */
    public double[] getHeight() {
        return height;
    }

    /**
     * Cuts a tree into several groups by specifying the desired number.
     * @param k the number of clusters.
     * @return the cluster label of each sample.
     */
    public int[] partition(int k) {
        int n = merge.length + 1;
        int[] membership = new int[n];

        IntHeapSelect heap = new IntHeapSelect(k);
        for (int i = 2; i <= k; i++) {
            heap.add(merge[n - i][0]);
            heap.add(merge[n - i][1]);
        }

        for (int i = 0; i < k; i++) {
            bfs(membership, heap.get(i), i);
        }

        return membership;
    }

    /**
     * Cuts a tree into several groups by specifying the cut height.
     * @param h the cut height.
     * @return the cluster label of each sample.
     */
    public int[] partition(double h) {
        for (int i = 0; i < height.length - 1; i++) {
            if (height[i] > height[i + 1]) {
                throw new IllegalStateException("Non-monotonic cluster tree -- the linkage is probably not appropriate!");
            }
        }
        
        if(h >= height[height.length - 1]) {
        	return new int[merge.length + 1];
        }

        int n = merge.length + 1;
        int k = 2;
        for (; k <= n; k++) {
            if (height[n - k] < h) {
                break;
            }
        }

        if (k <= 2) {
            throw new IllegalArgumentException("The parameter h is too large.");
        }

        return partition(k - 1);
    }

    /**
     * BFS the merge tree and identify cluster membership.
     * @param membership the cluster membership array.
     * @param cluster the last merge point of cluster.
     * @param id the cluster ID.
     */
    private void bfs(int[] membership, int cluster, int id) {
        int n = merge.length + 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(cluster);

        for (Integer i = queue.poll(); i != null; i = queue.poll()) {
            if (i < n) {
                membership[i] = id;
                continue;
            }

            i -= n;

            int m1 = merge[i][0];

            if (m1 >= n) {
                queue.offer(m1);
            } else {
                membership[m1] = id;
            }

            int m2 = merge[i][1];

            if (m2 >= n) {
                queue.offer(m2);
            } else {
                membership[m2] = id;
            }
        }
    }
	
}
