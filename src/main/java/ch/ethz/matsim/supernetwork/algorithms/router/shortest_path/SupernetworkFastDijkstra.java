/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.router.priorityqueue.BinaryMinHeap;
import org.matsim.core.router.util.ArrayRoutingNetwork;
import org.matsim.core.router.util.ArrayRoutingNetworkNode;
import org.matsim.core.router.util.DijkstraNodeData;
import org.matsim.core.router.util.DijkstraNodeDataFactory;
import org.matsim.core.router.util.NodeData;
import org.matsim.core.router.util.PreProcessDijkstra;
import org.matsim.core.router.util.RoutingNetwork;
import org.matsim.core.router.util.RoutingNetworkNode;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.utils.collections.RouterPriorityQueue;
import org.matsim.vehicles.Vehicle;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkFastDijkstra extends Dijkstra implements LeastCostTreeCalculator {

	private final RoutingNetwork routingNetwork;
	private final FastRouterDelegate fastRouter;
	private BinaryMinHeap<ArrayRoutingNetworkNode> heap = null;
	private int maxSize = -1;
	private Person person;
	private List<Id<Node>> targets;
	private int targetsCounter = 0;
    

	SupernetworkFastDijkstra(final RoutingNetwork routingNetwork, final TravelDisutility costFunction,
			final TravelTime timeFunction, final PreProcessDijkstra preProcessData,
			final FastRouterDelegateFactory fastRouterFactory, final Person person) {
		super(routingNetwork, costFunction, timeFunction, preProcessData);

		this.routingNetwork = routingNetwork;
		this.fastRouter = fastRouterFactory.createFastRouterDelegate(this, new DijkstraNodeDataFactory(),
				routingNetwork);
		this.nodeData.clear();
		this.person = person;

	}
	
	@Override
	public Path calcLeastCostPathFromPredecessorNode(Node toNode, double startTime, PredecessorNode[] pn) {
		return this.fastRouter.constructPathFromNodesTree(toNode, startTime, pn);
	}

	@Override
	public Path[] calcLeastCostTree(final Node fromNode, final List<Node> toNodes, final double startTime) {
		// computation of the disutility is indipendent from the person
		// (RandomizingTimeDistanceTravelDisutility.class) but the object is still
		// necessary
	    this.targets = new ArrayList<Id<Node>>();
		for (Node n : toNodes) {
			this.targets.add(this.routingNetwork.getNodes().get(n.getId()).getId());
		}
		Collections.sort(this.targets);
		targetsCounter = this.targets.size();
		calcLeastCostPath(fromNode, null, startTime, this.person, null);

		Path[] paths = new Path[toNodes.size()];
		for(int i=0;i<toNodes.size();i++) {
		
			paths[i] = this.fastRouter.constructPath(this.routingNetwork.getNodes().get(fromNode.getId()), this.routingNetwork.getNodes().get(toNodes.get(i).getId()), startTime, 0);
		}
		
		return paths;
	}

	/*
	 * Replace the references to the from and to nodes with their corresponding
	 * nodes in the routing network.
	 */
	@Override
	public Path calcLeastCostPath(final Node fromNode, final Node toNode, final double startTime, final Person person,
			final Vehicle vehicle) {

		this.fastRouter.initialize();
		this.routingNetwork.initialize();

		RoutingNetworkNode routingNetworkFromNode = this.routingNetwork.getNodes().get(fromNode.getId());

		augmentIterationId(); // this call makes the class not thread-safe
		this.person = person;
		this.vehicle = vehicle;

		RouterPriorityQueue<Node> pendingNodes = (RouterPriorityQueue<Node>) createRouterPriorityQueue();

        initFromNode(routingNetworkFromNode, toNode, startTime, pendingNodes);
		searchLogic(routingNetworkFromNode, toNode, pendingNodes);
		
		return null;
	}

	@Override
	/* package */ RouterPriorityQueue<? extends Node> createRouterPriorityQueue() {
		/*
		 * Re-use existing BinaryMinHeap instead of creating a new one. For large
		 * networks (> 10^6 nodes and links) this reduced the computation time by 40%!
		 * cdobler, oct'15
		 */
		if (this.routingNetwork instanceof ArrayRoutingNetwork) {
			int size = this.routingNetwork.getNodes().size();
			if (this.heap == null || this.maxSize != size) {
				this.maxSize = size;
				this.heap = new BinaryMinHeap<>(maxSize);
				return this.heap;
			} else {
				this.heap.reset();
				return this.heap;
			}
//			int maxSize = this.routingNetwork.getNodes().size();
//			return new BinaryMinHeap<ArrayRoutingNetworkNode>(maxSize);
		} else {
			return super.createRouterPriorityQueue();
		}
	}

	/*
	 * Constructs the path and replaces the nodes and links from the routing network
	 * with their corresponding nodes and links from the network.
	 */
	@Override
	protected Path constructPath(Node fromNode, Node toNode, double startTime, double arrivalTime) {
		return this.fastRouter.constructPath(fromNode, toNode, startTime, arrivalTime);
	}

	/*
	 * For performance reasons the outgoing links of a node are stored in the
	 * routing network in an array instead of a map. Therefore we have to iterate
	 * over an array instead of over a map.
	 */
	@Override
	protected void relaxNode(final Node outNode, final Node toNode, final RouterPriorityQueue<Node> pendingNodes) {
		this.fastRouter.relaxNode(outNode, toNode, pendingNodes);
	}

	/*
	 * The DijkstraNodeData is taken from the RoutingNetworkNode and not from a map.
	 */
	@Override
	protected DijkstraNodeData getData(final Node n) {
		return (DijkstraNodeData) this.fastRouter.getData(n);
	}

	/*
	 * The DeadEndData is taken from the RoutingNetworkNode and not from a map.
	 */
	@Override
	protected PreProcessDijkstra.DeadEndData getPreProcessData(final Node n) {
		return this.fastRouter.getPreProcessData(n);
	}

	@Override
	Node searchLogic(final Node fromNode, final Node toNode, final RouterPriorityQueue<Node> pendingNodes) {

		boolean stillSearching = true;

		while (stillSearching) {
			Node outNode = pendingNodes.poll();

//			if(outNode != null ) {				
//				int pos = Collections.binarySearch(this.targets,outNode.getId());
//				if (pos>0) {
//					targetsCounter--;
//				}
//			}

			if (outNode == null || targetsCounter <= 0) {
				stillSearching = false;
			} else {
//				if(this.targets.contains(outNode.getId())) {
//					targetsCounter--;
//				}
				if (Collections.binarySearch(this.targets,outNode.getId())>=0) {
					targetsCounter--;
				}
				relaxNode(outNode, toNode, pendingNodes);
			}
		}
		return toNode;
	}

	@Override
	void initFromNode(final Node fromNode, final Node toNode, final double startTime,
			final RouterPriorityQueue<Node> pendingNodes) {
		RoutingNetworkNode routingNetworkNode = (RoutingNetworkNode) fromNode;
		DijkstraNodeData data = (DijkstraNodeData) getData(routingNetworkNode);
		// visitNode(fromNode, data, pendingNodes, startTime, 0, null);
		for (Link l : routingNetworkNode.getOutLinksArray()) {
			Node firstNode = l.getToNode();
			DijkstraNodeData dataFirstNode = getData(firstNode);
			visitNode(firstNode, dataFirstNode, pendingNodes, startTime, 0, l);
		}
	}

}
