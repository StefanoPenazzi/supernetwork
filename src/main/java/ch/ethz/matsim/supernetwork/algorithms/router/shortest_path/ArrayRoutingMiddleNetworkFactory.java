/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.LinkFactory;
import org.matsim.core.router.util.AbstractRoutingNetworkFactory;
import org.matsim.core.router.util.ArrayRoutingNetwork;
import org.matsim.core.router.util.ArrayRoutingNetworkFactory;
import org.matsim.core.router.util.ArrayRoutingNetworkLink;
import org.matsim.core.router.util.ArrayRoutingNetworkNode;
import org.matsim.core.router.util.RoutingNetworkLink;
import org.matsim.core.router.util.RoutingNetworkNode;

/**
 * @author stefanopenazzi
 *
 */
public class ArrayRoutingMiddleNetworkFactory extends AbstractRoutingNetworkFactory  {
	
	
	private int nodeArrayIndexCounter;
	private int linkArrayIndexCounter;

	@Override
	public synchronized ArrayRoutingNetwork createRoutingNetwork(final Network network) {
		ArrayRoutingNetwork routingNetwork = new ArrayRoutingNetwork(network);
		return routingNetwork;
	}
	
	public synchronized ArrayRoutingNetwork createRoutingMiddleNetwork(final List<Node> subNetworkNodes, final List<Link> subNetworkLinks, final Node root, final List<Link> middleNetworkLinks) {
		this.nodeArrayIndexCounter = 0;
		this.linkArrayIndexCounter = 0;
		List<Node> networkNodes = subNetworkNodes;
		networkNodes.add(root);
		List<Link> networkLinks = subNetworkLinks;
		networkLinks.addAll(middleNetworkLinks);
		
		ArrayRoutingNetwork routingNetwork = new ArrayRoutingNetwork(null);
		
		for (Node node : networkNodes) {
			RoutingNetworkNode routingNode = createRoutingNetworkNode(node, node.getOutLinks().size());
			routingNetwork.addNode(routingNode);
		}
		Map<Id<Link>, RoutingNetworkLink> routingLinks = new HashMap<Id<Link>, RoutingNetworkLink>();
		for (Link link : networkLinks) {
			RoutingNetworkNode fromNode = routingNetwork.getNodes().get(link.getFromNode().getId());
			RoutingNetworkNode toNode = routingNetwork.getNodes().get(link.getToNode().getId());
			RoutingNetworkLink dijkstraLink = createRoutingNetworkLink(link, fromNode, toNode);
			routingLinks.put(dijkstraLink.getId(), dijkstraLink);
		}
		
		for (Node node : networkNodes) {
			RoutingNetworkLink[] outLinks = new RoutingNetworkLink[node.getOutLinks().size()];
			
			int i = 0;
			for (Link outLink : node.getOutLinks().values()) {
				outLinks[i] = routingLinks.remove(outLink.getId());
				i++;
			}
			
			RoutingNetworkNode dijkstraNode = routingNetwork.getNodes().get(node.getId());
			dijkstraNode.setOutLinksArray(outLinks);
		}
		
		return routingNetwork;
	}

	@Override
	public ArrayRoutingNetworkNode createRoutingNetworkNode(final Node node, final int numOutLinks) {
		return new ArrayRoutingNetworkNode(node, numOutLinks, this.nodeArrayIndexCounter++);
	}

	@Override
	public ArrayRoutingNetworkLink createRoutingNetworkLink(final Link link, final RoutingNetworkNode fromNode, final RoutingNetworkNode toNode) {
		return new ArrayRoutingNetworkLink(link, fromNode, toNode, this.linkArrayIndexCounter++);
	}

	@Override
	public void setLinkFactory(final LinkFactory factory) {
		throw new RuntimeException("not implemented");
	}

}
