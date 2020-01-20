/**
 * 
 */
package ch.ethz.matsim.supernetwork.halfnetwork;

import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.CALNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public class HalfnetworkImpl implements Halfnetwork {

	private Cluster cluster;
	private Subnetwork subnetwork;
	private TrafficDataContainer trafficDataContainer;
	private Node node;
	private List<Link> superLinks;
	
	public HalfnetworkImpl(TrafficDataContainer trafficDataContainer,Cluster cluster,Subnetwork subnetwork) {
		this.trafficDataContainer = trafficDataContainer;
		this.cluster = cluster;
		this.subnetwork = subnetwork;
	}
	
	@Override
	public Cluster getCluster() {
		// TODO Auto-generated method stub
		return this.cluster;
	}

	@Override
	public Subnetwork getSubnetwork() {
		// TODO Auto-generated method stub
		return this.subnetwork;
	}

	@Override
	public TrafficDataContainer getTrafficDataContainer() {
		// TODO Auto-generated method stub
		return this.trafficDataContainer;
	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return this.node;
	}

	@Override
	public List<Link> getSuperLinks() {
		// TODO Auto-generated method stub
		return this.superLinks;
	}

	private void setCoord() {
		this.node = null;
	}
	private void setSuperLinks() {
		
		for(Node n: ((CALNetworkRegionImpl)cluster).getNodes()){
			
		}
	}
}
