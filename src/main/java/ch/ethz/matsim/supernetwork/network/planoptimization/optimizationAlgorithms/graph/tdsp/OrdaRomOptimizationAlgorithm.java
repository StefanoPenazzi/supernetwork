/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Node;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp.TdspGraphOrdaRom;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalLink;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalNode;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class OrdaRomOptimizationAlgorithm implements OptimizationAlgorithm {

	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	private final ContainerManager containerManager;
	private final PopulationFactory populationFactory;
	
	private double[] startTimes;
	private double[][] arrivalTime;
	private double[][] permanentLabels;
	private SortedMap<LinkTimeKey,Double> tempLabelsMap;
	private TdspIntermodalGraph graph;
	private boolean finish = false;
	
	
	public OrdaRomOptimizationAlgorithm(ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,ContainerManager containerManager,PopulationFactory populationFactory) {
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		this.containerManager = containerManager;
		this.populationFactory = populationFactory;
	}
	
	private void init(TdspIntermodalGraph graph,double[] startTimes,int rootNodeId) {
		
		this.graph = graph;
		
		this.startTimes = startTimes;
		
		finish = false;
		
		permanentLabels = new double[graph.getNodes().length][startTimes.length];
		Arrays.fill(permanentLabels, Double.MAX_VALUE);
		
		arrivalTime = new double[graph.getNodes().length][startTimes.length];
		Arrays.fill(arrivalTime, -1);
		
		tempLabelsMap = new TreeMap<LinkTimeKey,Double>();
		for(Node n: graph.getNodes()) {
			for(Link l: n.getOutLinks()) {
				for(int k = 0;k>startTimes.length;k++) {
					LinkTimeKey lk = new LinkTimeKey(l.getFromNodeId(),l.getToNodeId(),k);
					tempLabelsMap.put(lk, Double.MAX_VALUE);
				}
			}
		}
		
		for(int i =0;i<this.startTimes.length;i++) {
			permanentLabels[rootNodeId][i] = this.startTimes[i];
		}
		
	}
	
	private void setPermanentLabels() {
		finish = true;
		for(int j = 0;j<graph.getNodes().length;j++) {
			for(int t = 0;t<startTimes.length;t++) {
				double min = Double.MAX_VALUE;
			    for(Link l: graph.getNodes()[j].getInLinks()) {
			    	LinkTimeKey ltk = new LinkTimeKey(l.getFromNodeId(),j,t);
			    	double tempUf = tempLabelsMap.get(ltk);
					if(min>tempUf) {
						min = tempUf;
					}
				}
			    if(permanentLabels[j][t] != min) {
			    	permanentLabels[j][t] = min;
			    	finish = false;
			    }
			}
		}
	}
	
	private void setTemporaryLabels() {
		for (LinkTimeKey ltk : tempLabelsMap.keySet()) {
			double permanentLabel = permanentLabels[ltk.fromNode][ltk.getTime()];
			if(permanentLabel != Double.MAX_VALUE) {
				double label = 0;
				//TODO this can be done with an array of links
				TdspIntermodalLink link = null;
				for(Link l: graph.getNodes()[ltk.fromNode].getOutLinks()) {
					if(link.getToNodeId() == ltk.getToNode()) {
						link = (TdspIntermodalLink )l;
					}
				}
				if(link.getType() == "depStart") {
					
					TdspIntermodalNode fromNode = (TdspIntermodalNode)this.graph.getNodes()[ltk.fromNode];
					TdspIntermodalNode toNode = (TdspIntermodalNode)this.graph.getNodes()[ltk.toNode];
					
					switch(link.getMode()) {
					case "car":
						
						Leg leg = this.populationFactory.createLeg( "car" );
						double travelTime = (containerManager.getPath(fromNode.getActivity(),toNode.getActivity() , arrivalTime[ltk.fromNode][ltk.getTime()],fromNode.getMode())).travelTime;
						leg.setTravelTime(travelTime);
						double uf = this.scoringFunctionForPopulationGraph.getLegUtilityFunctionValueForAgent(graph.getPerson().getId(), leg);
						label = permanentLabel - uf;
						tempLabelsMap.put(ltk, label);
						arrivalTime[ltk.toNode][ltk.getTime()] = arrivalTime[ltk.fromNode][ltk.getTime()] + travelTime;
						
						break;
						
					case "walk":
						break;
						
					case "bike":
						break;
						
					case "ride":
					    break;
					    
					case "pt":
						break;
					default:
					
					}
					
				}else if(link.getType() == "startEnd") {
					label = permanentLabel - link.getDuration();
					tempLabelsMap.put(ltk, label);
					arrivalTime[ltk.toNode][ltk.getTime()] = arrivalTime[ltk.fromNode][ltk.getTime()] + link.getDuration();
				}
				else {
					label = permanentLabel;
					tempLabelsMap.put(ltk, label);
				}
			}
		}
	}
	
	private List<? extends PlanElement> buildSolution(int firstActivityNodeId, int lastActivityNodeId) {
		
		double min = Double.MAX_VALUE;
		int minStartTime = 0;
		List<Integer> predecessorsList = new ArrayList<>(); 
		
		for(int i = 0;i<startTimes.length;i++) {
			if(min>permanentLabels[lastActivityNodeId][i]) {
				min = permanentLabels[lastActivityNodeId][i];
				minStartTime = i;
			}
		}
		
		boolean rootFind = false;
		int currActivityNode = lastActivityNodeId;
		int previousActivityNode = 0;
		predecessorsList.add(lastActivityNodeId);
		
		while(!rootFind) {
			min = Double.MAX_VALUE;
			for(Link l: graph.getNodes()[currActivityNode].getInLinks()) {
				LinkTimeKey ltk = new LinkTimeKey(l.getFromNodeId(),l.getToNodeId(),minStartTime);
				if(min>tempLabelsMap.get(ltk)) {
					previousActivityNode = l.getFromNodeId();
				}
			}
			
			predecessorsList.add(previousActivityNode);
			
			if(previousActivityNode == firstActivityNodeId) {
				return null;
			}
			else {
				currActivityNode = previousActivityNode;
			}
		}
		
		return null;
	}
	
	@Override
	public List<? extends PlanElement> run(PlanModel planModel) {
		TdspIntermodalGraph graph = (TdspIntermodalGraph)planModel;
		//TODO startTimes
		init(graph, new double[5],0);
		while(!finish) {
			setTemporaryLabels();
			setPermanentLabels();
		}
		
		return buildSolution(0,graph.getNodes().length);
	}

	 class LinkTimeKey implements Comparable<LinkTimeKey> {

		 private final int fromNode;
		 private final int toNode;
		 private final int time;
		 
		 public LinkTimeKey(int fromNode,int toNode, int time) {
			 this.fromNode = fromNode;
			 this.toNode = toNode;
			 this.time = time;
		 }
		 
		 public int getFromNode() {
			 return this.fromNode;
		 }
		 public int getToNode() {
			 return this.toNode;
		 }
		 
		public int getTime() {
			return this.time;
		}
		 
		@Override
		public int compareTo(LinkTimeKey arg0) {
			
			if(this.fromNode > arg0.getFromNode()) {
				return 1;
			}
			else if(this.fromNode < arg0.fromNode) {
				return -1;
			}
			else {
				if(this.toNode > arg0.getToNode()) {
					return 1;
				}
				else if(this.toNode < arg0.getToNode()) {
					return -1;
				}
				else {
					if(this.time > arg0.getTime()) {
						return 1;
					}
					else if(this.time< arg0.getTime()) {
						return -1;
					}
					else {
						return 0;
					}
				}
			}
		}
	}
}
