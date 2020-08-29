/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.NodeData;
import org.matsim.core.router.util.PreProcessDijkstra;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.core.utils.collections.RouterPriorityQueue;

/**
 * This class is used by the faster implementations of the Dijkstra, AStarEuclidean and
 * AStarLandmarks router. Basically, the methods perform the conversation from the
 * Network to the RoutingNetwork where the routing data is stored in the nodes and not
 * in maps.
 * 
 * @author cdobler
 */
/*package*/ interface FastRouterDelegate {
	
	/*
	 * Some implementations might use this for lazy initialization.
	 */
	/*package*/ void initialize();
	
	/*
	 * Constructs the path and replaces the nodes and links from the routing network
	 * with their corresponding nodes and links from the network.
	 */
	/*package*/ Path constructPath(Node fromNode, Node toNode, double startTime, double arrivalTime);	
	/*
	 * For performance reasons the outgoing links of a node are stored in
	 * the routing network in an array instead of a map. Therefore we have
	 * to iterate over an array instead of over a map. 
	 */
	
	PredecessorNode[] constructNodesTree();
	
	Path constructPathFromNodesTree(Node toNode, double startTime ,PredecessorNode[] pn);	
	
	/*package*/ void relaxNode(final Node outNode, final Node toNode, final RouterPriorityQueue<Node> pendingNodes);	

	/*
	 * The NodeData is taken from the RoutingNetworkNode and not from a map.
	 */
	/*package*/ NodeData getData(final Node n);

	/*
	 * The DeadEndData is taken from the RoutingNetworkNode and not from a map.
	 */
	/*package*/ PreProcessDijkstra.DeadEndData getPreProcessData(final Node n);
	
	NodeData[] getNodeDataArray();
	
}
