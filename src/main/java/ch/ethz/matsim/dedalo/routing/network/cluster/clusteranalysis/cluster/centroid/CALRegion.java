/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster.centroid;

import java.util.List;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;


/**
 * @author stefanopenazzi
 *
 */
public class CALRegion extends ClusterActivitiesLocation {
	
	private List<Node> regionNodes;
	
	public CALRegion(int id) {
		super(id);
	}
	
	public CALRegion(int id,List<ElementActivity> activities,Coord centroid,List<Node> regionNodes) {
		super(id,activities,centroid);
		this.regionNodes = regionNodes;
	}
	
	public void setRegionNodes(List<Node> regionNodes) {
		this.regionNodes = regionNodes;
	}
	
	public List<Node> getRegionNodes() {
		return this.regionNodes;
	}

}
