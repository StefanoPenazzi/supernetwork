/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import org.matsim.api.core.v01.network.Node;

import org.matsim.core.router.util.ArrayRoutingNetwork;
import org.matsim.core.router.util.ArrayRoutingNetworkNode;
import org.matsim.core.router.util.NodeData;
import org.matsim.core.router.util.NodeDataFactory;

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
}