/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.models.connectivity.AgglomerativeHierarchical;

/**
 * @author stefanopenazzi
 *
 */
public class IntHeapSelect {

	/**
     * The heap size.
     */
    private int k;
    /**
     * The number of objects that have been added into heap.
     */
    private int n;
    /**
     * True if the heap is fully sorted.
     */
    private boolean sorted;
    /**
     * The heap array.
     */
    private int[] heap;

    /**
     * Constructor.
     * @param k the heap size.
     */
    public IntHeapSelect(int k) {
        this(new int[k]);
    }

    /**
     * Constructor.
     * @param heap the array to store smallest values to track.
     */
    public IntHeapSelect(int[] heap) {
        this.heap = heap;
        k = heap.length;
        n = 0;
        sorted = false;
    }

    /**
     * Assimilate a new value from the stream.
     */
    public void add(int datum) {
        sorted = false;
        if (n < k) {
            heap[n++] = datum;
            if (n == k) {
                heapify(heap);
            }
        } else {
            n++;
            if (datum < heap[0]) {
                heap[0] = datum;
                Sort.siftDown(heap, 0, k-1);
            }
        }
    }

    /**
     * Returns the k-<i>th</i> smallest value seen so far.
     */
    public int peek() {
        return heap[0];
    }

    /**
     * Returns the i-<i>th</i> smallest value seen so far. i = 0 returns the smallest
     * value seen, i = 1 the second largest, ..., i = k-1 the last position
     * tracked. Also, i must be less than the number of previous assimilated.
     */
    public int get(int i) {
        if (i > Math.min(k, n) - 1) {
            throw new IllegalArgumentException("HeapSelect i is greater than the number of data received so far.");
        }

        if (i == k-1) {
            return heap[0];
        }
        
        if (!sorted) {
            sort(heap, Math.min(k,n));
            sorted = true;
        }

        return heap[k-1-i];
    }

    /**
     * Sort the smallest values.
     */
    public void sort() {
        if (!sorted) {
            sort(heap, Math.min(k,n));
            sorted = true;
        }
    }

    /**
     * Place the array in max-heap order. Note that the array is not fully sorted.
     */
    private static void heapify(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            Sort.siftDown(arr, i, n - 1);
    }

    /**
     * Sorts the specified array into descending order. It is based on Shell
     * sort, which is very efficient because the array is almost sorted by
     * heapifying.
     */
    private static void sort(int[] a, int n) {
        int inc = 1;
        do {
            inc *= 3;
            inc++;
        } while (inc <= n);

        do {
            inc /= 3;
            for (int i = inc; i < n; i++) {
                int v = a[i];
                int j = i;
                while (a[j - inc] < v) {
                    a[j] = a[j - inc];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                a[j] = v;
            }
        } while (inc > 1);
    }
}
