/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;

import org.matsim.core.router.util.ArrayRoutingNetwork;
import org.matsim.core.router.util.ArrayRoutingNetworkNode;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.dedalo.routing.router.cluster.PredecessorNode;

import org.matsim.core.router.util.NodeData;
import org.matsim.core.router.util.NodeDataFactory;
import org.matsim.core.router.util.RoutingNetworkLink;
import org.matsim.core.router.util.RoutingNetworkNode;

/**
 * @author stefanopenazzi
 *
 */
class ArrayFastRouterDelegate extends AbstractFastRouterDelegate {

	private final ArrayRoutingNetwork network;
	private final NodeData[] nodeData;
	private boolean isInitialized = false;
	
	/*package*/ ArrayFastRouterDelegate(final Dijkstra dijkstra, final NodeDataFactory nodeDataFactory,
			final ArrayRoutingNetwork network) {
		super(dijkstra, nodeDataFactory);
		this.network = network;
		this.nodeData = new NodeData[network.getNodes().size()];
	}

	@Override
	public final void initialize() {
		// lazy initialization
		if (!isInitialized) {
			for (Node node : this.network.getNodes().values()) {
				int index = ((ArrayRoutingNetworkNode) node).getArrayIndex();
				this.nodeData[index] = nodeDataFactory.createNodeData();
			}
			
			this.isInitialized = true;
		}
	}
	
	/*
	 * The NodeData is taken from the array.
	 */
	public NodeData getData(final Node n) {
		ArrayRoutingNetworkNode routingNetworkNode = (ArrayRoutingNetworkNode) n;
		return this.nodeData[routingNetworkNode.getArrayIndex()];
	}
	
	public NodeData[] getNodeDataArray() {
		return this.nodeData;
	}
	
	
	@Override
	public PredecessorNode[] constructNodesTree(){
		PredecessorNode[] predecessorNodes = new PredecessorNode[this.nodeData.length];
		for(int i = 0;i < this.nodeData.length;i++) {
			if(this.nodeData[i].getPrevLink()==null) {
				predecessorNodes[i] = new PredecessorNode(-1,nodeData[i].getTime(),nodeData[i].getCost(),null);
			}
			else {
				ArrayRoutingNetworkNode routingNetworkNode = (ArrayRoutingNetworkNode)nodeData[i].getPrevLink().getFromNode();
				int pn = routingNetworkNode.getArrayIndex();
				predecessorNodes[i] = new PredecessorNode(pn,nodeData[i].getTime(),nodeData[i].getCost(),((RoutingNetworkLink)nodeData[i].getPrevLink()).getLink());
			}
		}
		
		//this should be unmodifiable but I don't know the impact on performance
		return predecessorNodes;
	}

	@Override
	public Path constructPathFromNodesTree(Node toNode, double startTime, PredecessorNode[] pn) {
		
		ArrayList<Node> nodes = new ArrayList<>();
		ArrayList<Link> links = new ArrayList<>();
		int index = ((ArrayRoutingNetworkNode)network.getNodes().get(toNode.getId())).getArrayIndex();
		double cost = pn[index].getCost();
		double arrivalTime = pn[index].getTime();
		boolean pass = true;
		
		nodes.add(0, toNode);
		links.add(0,pn[index].getLink());
		index = pn[index].getPredecessor();
		arrivalTime = pn[index].getTime();
		// last node is the supernode. this must not be in the path
		pass = (pn[index].getLink().equals(null)) ? false : true;

		while (pass) {
			Link l = pn[index].getLink();
			links.add(0,l);
			nodes.add(0,l.getFromNode());
			index = pn[index].getPredecessor();
			// last node is the supernode. this must not be in the path
			pass = (pn[index].getLink().equals(null)) ? false : true;
		}
		return new Path(nodes, links, arrivalTime,cost);
	}
}