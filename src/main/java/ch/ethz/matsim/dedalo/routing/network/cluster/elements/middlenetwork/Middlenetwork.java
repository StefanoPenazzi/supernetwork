/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork;

import java.util.List;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster.Cluster;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlelink.Middlelink;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.subnetwork.Subnetwork;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public interface Middlenetwork {
	
	public Cluster getCluster();
	public Subnetwork getSubnetwork();
	public Supernode getSuperNode();
	public void setSuperNode(Supernode node);
	public List<Middlelink> getMiddleLinks();
	public void setMiddleLinks(List<Middlelink> middleLinks);
	public void setToNodes(List<Node> toNodes);
	public List<Node> getToNodes();
	

}
