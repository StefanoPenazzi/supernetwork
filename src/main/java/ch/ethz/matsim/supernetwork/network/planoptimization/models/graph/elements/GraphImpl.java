/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp.TdspLink;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public abstract class GraphImpl implements Graph {
	
	private List<Node> nodesList;
	private List<Link> linksList;
	private Node[] nodes;
	private Link[] links;
	private boolean finish = false;
	private final OptimizationAlgorithm optimizationAlgorithm;
	
	public GraphImpl(OptimizationAlgorithm optimizationAlgorithm) {
		nodesList = new ArrayList<>();
		linksList = new ArrayList<>();
		this.optimizationAlgorithm = optimizationAlgorithm;
	}

	@Override
	public Node[] getNodes() {
		// TODO Auto-generated method stub
		return nodes;
	}

	@Override
	public Link[] getLinks() {
		// TODO Auto-generated method stub
		return links;
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

	@Override
	public OptimizationAlgorithm getOptimizationAlgorithm() {
		// TODO Auto-generated method stub
		return this.optimizationAlgorithm;
	}


	@Override
	public
	abstract List<? extends PlanElement> getNewPlan();

}
