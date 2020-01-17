/**
 * 
 */
package ch.ethz.matsim.supernetwork.cluster_analysis.models.connectivity.AgglomerativeHierarchical;

/**
 * @author stefanopenazzi
 * 
 * Ward’s minimum variance method joins the two clusters and that minimise the increase
 * in the sum of squared errors. 
 * https://www.ufs.ac.za/docs/librariesprovider22/mathematical-statistics-and-actuarial-science-documents/technical-reports-documents/teg437-3941-eng.pdf?sfvrsn=303ef921_0
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

        //The initial cluster distances in Ward's minimum variance method are
        //therefore defined to be the squared Euclidean distance between points
        for (int i = 0; i < proximity.length; i++) {
            proximity[i] *= proximity[i];
        }
    }

    @Override
    public String toString() {
        return "Ward's linkage";
    }

	/* Lance-Williams Algorithm. the updated proximity value is done only on the first cluster in which the second
	 * is merged */
    
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
