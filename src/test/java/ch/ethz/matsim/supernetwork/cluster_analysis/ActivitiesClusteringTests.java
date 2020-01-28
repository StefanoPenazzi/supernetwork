/**
 * 
 */
package ch.ethz.matsim.supernetwork.cluster_analysis;

import org.junit.jupiter.api.Test;

import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.kd_tree.KDTreeClustersContainer;

/**
 * @author stefanopenazzi
 *
 */
public class ActivitiesClusteringTests {
	
	@Test
	public void kdTreeTest() {
		KDTreeClustersContainer container = new KDTreeClustersContainer(null,100);
	}

}
