/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork;

import java.util.List;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.network.networkelements.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.network.networkelements.supernode.Supernode;

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
