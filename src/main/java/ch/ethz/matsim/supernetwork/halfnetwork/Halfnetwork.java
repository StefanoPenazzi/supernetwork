/**
 * 
 */
package ch.ethz.matsim.supernetwork.halfnetwork;

import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface Halfnetwork {
	
	public Cluster getCluster();
	public Subnetwork getSubnetwork();
	public TrafficDataContainer getTrafficDataContainer();
	public Node getNode();
	public List<Link> getSuperLinks();

}
