/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkelements.middlenetwork;

import java.util.List;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.networkelements.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.networkelements.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkImpl implements Middlenetwork {

		private Cluster cluster;
		private Subnetwork subnetwork;
		private Supernode node;
		private List<Middlelink> middleLinks;
		private List<Node> toNodes;
		
		public MiddlenetworkImpl(Cluster cluster,Subnetwork subnetwork) {
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

		@Override
		public void setToNodes(List<Node> toNodes) {
			this.toNodes = toNodes;
			
		}

		@Override
		public List<Node> getToNodes() {
			return this.toNodes;
		}
	}

