/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.AgglomerativeHierarchical;

/**
 * @author stefanopenazzi
 *
 */
public class WardLinkage extends Linkage {

	/**
     * The number of samples in each cluster.
     */
    private int[] n;

    /**
     * Constructor.
     * @param proximity  the proximity matrix to store the distance measure of
     * dissimilarity. To save space, we only need the lower half of matrix.
     */
    public WardLinkage(double[][] proximity) {
        super(proximity);
        init();
    }

    /** Initialize sample size. */
    private void init() {
        n = new int[size];
        for (int i = 0; i < size; i++) {
            n[i] = 1;
        }

        for (int i = 0; i < proximity.length; i++) {
            proximity[i] *= proximity[i];
        }
    }

    @Override
    public String toString() {
        return "Ward's linkage";
    }

    @Override
    public void merge(int i, int j) {
        float nij = n[i] + n[j];

        for (int k = 0; k < i; k++) {
            proximity[index(i, k)] = (d(i, k) * (n[i] + n[k]) + d(j, k) * (n[j] + n[k]) - d(j, i) * n[k]) / (nij + n[k]);
        }

        for (int k = i+1; k < j; k++) {
            proximity[index(k, i)] = (d(k, i) * (n[i] + n[k]) + d(j, k) * (n[j] + n[k]) - d(j, i) * n[k]) / (nij + n[k]);
        }

        for (int k = j+1; k < size; k++) {
            proximity[index(k, i)] = (d(k, i) * (n[i] + n[k]) + d(k, j) * (n[j] + n[k]) - d(j, i) * n[k]) / (nij + n[k]);
        }

        n[i] += n[j];
    }
}
