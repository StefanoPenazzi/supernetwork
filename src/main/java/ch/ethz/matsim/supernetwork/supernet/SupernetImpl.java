/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;

import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.halfnetwork.Halfnetwork;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetImpl implements Supernet{

	private  List<Halfnetwork> halfnetworks;
	private ClustersContainer clusterContainer;
	
	
	public SupernetImpl(ClustersContainer clusterContainer, List<Halfnetwork> halfnetworks) {
		this.clusterContainer = clusterContainer;
		this.halfnetworks =halfnetworks;
	}
	
	@Override
	public List<Halfnetwork> getHalfnetworks() {
		
		return this.halfnetworks;
	}

	@Override
	public ClustersContainer getClusterContainer() {
		// TODO Auto-generated method stub
		return this.clusterContainer;
	}

}
