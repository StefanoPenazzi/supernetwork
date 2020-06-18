/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

import java.util.HashMap;
import java.util.List;
import org.matsim.api.core.v01.population.PlanElement;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public abstract class GraphImpl implements Graph {
	
	private Node[] nodes;
	private Link[] links;
	private boolean finish = false;
	private final OptimizationAlgorithm optimizationAlgorithm;
	
	public GraphImpl(OptimizationAlgorithm optimizationAlgorithm) {
		this.optimizationAlgorithm = optimizationAlgorithm;
	}

	@Override
	public Node[] getNodes() {
		return nodes;
	}

	@Override
	public Link[] getLinks() {
		return links;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void buildLinksNotIntoNodes(List<? extends Node> nodesList, List<? extends Link> linksList) {
		finish = true;
		nodes =  new Node[nodesList.size()];
		nodesList.toArray(nodes);
		links = new Link[linksList.size()];
		linksList.toArray(links); 
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
	public void buildLinksIntoNodes(List<? extends Node> nodesList) {
		
		finish = true;
		
		nodes =  new Node[nodesList.size()];
		nodesList.toArray(nodes);
		
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
