/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp.TdspLink;

/**
 * @author stefanopenazzi
 *
 */
public class GraphImpl implements Graph {
	
	private List<Node> nodesList;
	private List<Link> linksList;
	private Node[] nodes;
	private Link[] links;
	private boolean finish = false;
	
	public GraphImpl() {
		nodesList = new ArrayList<>();
		linksList = new ArrayList<>();
	}

	@Override
	public Node[] getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link[] getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildLinksNotIntoNodes() {
		finish = true;
		nodes = new Node[nodesList.size()];
		links = new Link[linksList.size()];
		HashMap<Integer,Integer> newNodeId = new HashMap<Integer,Integer>(); 
		// map with the node id and the new id equal to the position in th
		nodesList.toArray(nodes);
		for(int i =0;i<nodes.length;++i) {
			newNodeId.put(nodes[i].getId(), i);
			nodes[i].setId(i);
		}
		//TODO exception in case the from/to node can not be found
		for (Link l: linksList) {
			int newFromNodeId = newNodeId.get(l.getFromNodeId());
			int newToNodeId = newNodeId.get(l.getToNodeId());
			l.setFromNodeId(newFromNodeId);
			l.setToNodeId(newToNodeId);
			nodes[newFromNodeId].getOutLinks().add(l);
			nodes[newToNodeId].getInLinks().add(l);
		}
	}

	@Override
	public void addNode(Node node) {
		if(!finish) {
			nodesList.add(node);
		}
		else {
			//TODO exception
		}
	}

	@Override
	public void addLink(Link link) {
		if(!finish) {
			linksList.add((TdspLink)link);
		}
		else {
			// exception
		}
		
	}

	@Override
	public void buildLinksIntoNodes() {
		
		finish = true;
		nodes = new Node[nodesList.size()];
		HashMap<Integer,Integer> newNodeId = new HashMap<Integer,Integer>(); 
		// map with the node id and the new id equal to the position in th
		nodesList.toArray(nodes);
		for(int i =0;i<nodes.length;++i) {
			newNodeId.put(nodes[i].getId(), i);
			//TODO exception in case the from/to node can not be found
			nodes[i].setId(i);
		}
		//TODO exception in case the from/to node can not be found
		for (int i =0;i<nodes.length;++i) {
			
			for(Link l: nodes[i].getInLinks()) {
				l.setFromNodeId(newNodeId.get(l.getFromNodeId()));
				l.setToNodeId(i);
			}
			
            for(Link l: nodes[i].getOutLinks()) {
            	l.setFromNodeId(i);
				l.setToNodeId(l.getToNodeId());
			}
		}
		
	}

}
