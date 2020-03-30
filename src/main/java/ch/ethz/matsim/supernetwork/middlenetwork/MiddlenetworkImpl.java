/**
 * 
 */
package ch.ethz.matsim.supernetwork.middlenetwork;

import java.util.List;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkImpl implements Middlenetwork {

		private Cluster cluster;
		private Subnetwork subnetwork;
		private TrafficDataContainer trafficDataContainer;
		private Supernode node;
		private List<Middlelink> middleLinks;
		
		public MiddlenetworkImpl(TrafficDataContainer trafficDataContainer,Cluster cluster,Subnetwork subnetwork) {
			this.trafficDataContainer = trafficDataContainer;
			this.cluster = cluster;
			this.subnetwork = subnetwork;
		}
		
		@Override
		public Cluster getCluster() {
			return this.cluster;
		}

		@Override
		public Subnetwork getSubnetwork() {
			return this.subnetwork;
		}

		@Override
		public TrafficDataContainer getTrafficDataContainer() {
			return this.trafficDataContainer;
		}

		@Override
		public Supernode getSuperNode() {
			return this.node;
		}
		
		@Override
		public void setSuperNode(Supernode node) {
			this.node = node;
		}

		@Override
		public List<Middlelink> getMiddleLinks() {
			// TODO Auto-generated method stub
			return this.middleLinks;
		}

		private void setCoord() {
			this.node = null;
		}
		public void setMiddleLinks(List<Middlelink> middleLinks) {
			this.middleLinks = middleLinks;
		}
	}

