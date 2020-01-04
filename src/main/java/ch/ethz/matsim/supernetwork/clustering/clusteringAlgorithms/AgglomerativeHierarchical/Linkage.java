/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.AgglomerativeHierarchical;

/**
 * @author stefanopenazzi
 *
 */
public abstract class Linkage {
	
	private int size;
    private float[] proximity;

    /** Initialize the linkage with the lower triangular proximity matrix. */
    public Linkage(double[][] proximity) {
        this.size = proximity.length;
        this.proximity = new float[size * (size+1) / 2];
        // column wise
        for (int j = 0, k = 0; j < size; j++) {
            for (int i = j; i < size; i++, k++) {
                this.proximity[k] = (float) proximity[i][j];
            }
        }
    }

    int index(int i, int j) {
        return i > j ? proximity.length - (size-j)*(size-j+1)/2 + i - j : proximity.length - (size-i)*(size-i+1)/2 + j - i;
    }

    /** Returns the proximity matrix size. */
    public int size() {
        return size;
    }

    /**
     * Returns the distance/dissimilarity between two clusters/objects, which
     * are indexed by integers.
     */
    public float d(int i, int j) {
        return proximity[index(i, j)];
    }

    public abstract void merge(int i, int j);

}
