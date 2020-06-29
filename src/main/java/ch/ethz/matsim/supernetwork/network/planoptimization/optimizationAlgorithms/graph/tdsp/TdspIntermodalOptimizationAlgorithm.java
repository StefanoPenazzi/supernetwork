/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.population.routes.RouteUtils;
import org.matsim.core.router.RoutingModule;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Node;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalLink;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalNode;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp.OrdaRomOptimizationAlgorithm.LinkTimeKey;

/**
 * @author stefanopenazzi
 *
 */
public class TdspIntermodalOptimizationAlgorithm extends OrdaRomOptimizationAlgorithm {
	
	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	private final ContainerManager containerManager;
	private final PopulationFactory populationFactory;
	private final Network network;
	private final TripRouter tripRouter;
	private final ActivityFacilities facilities;
	
	private double[] startTimes;
	private double[][] arrivalTime;
	private double[][] permanentLabels;
	private SortedMap<LinkTimeKey,Double> tempLabelsMap;
	private TdspIntermodalGraph graph;
	private boolean finish = false;
	
	
	public TdspIntermodalOptimizationAlgorithm(ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,ContainerManager containerManager,PopulationFactory populationFactory
			,Network network,TripRouter tripRouter,ActivityFacilities facilities) {
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		this.containerManager = containerManager;
		this.populationFactory = populationFactory;
		this.network = network;
		this.tripRouter = tripRouter;
		this.facilities = facilities;
	}
	
	
	public void init(GraphImpl g) {
		
		this.graph = (TdspIntermodalGraph)g;
		
		this.startTimes = graph.getStartTimes();
		
		finish = false;
		
		permanentLabels = new double[graph.getNodes().length][startTimes.length];
		arrivalTime = new double[graph.getNodes().length][startTimes.length];
		for(int i=0;i<graph.getNodes().length;i++) {
			for(int j = 0;j<startTimes.length;j++) {
				permanentLabels[i][j] = Double.MAX_VALUE;
				arrivalTime[i][j] = Double.MAX_VALUE;
			}
		}
		
		tempLabelsMap = new TreeMap<LinkTimeKey,Double>();
		for(Node n: graph.getNodes()) {
			for(Link l: n.getOutLinks()) {
				for(int k = 0;k<startTimes.length;k++) {
					LinkTimeKey lk = new LinkTimeKey(l.getFromNodeId(),l.getToNodeId(),k);
					tempLabelsMap.put(lk, Double.MAX_VALUE);
				}
			}
		}
		
		for(int i =0;i<this.startTimes.length;i++) {
			arrivalTime[graph.getRootId()][i] = this.startTimes[i];
			permanentLabels[graph.getRootId()][i] = 0;
		}
		
	}
	
	public void setPermanentLabels() {
		finish = true;
		for(int j = 1;j<graph.getNodes().length;j++) {
			for(int t = 0;t<startTimes.length;t++) {
				double min = Double.MAX_VALUE;
			    for(Link l: graph.getNodes()[j].getInLinks()) {
			    	LinkTimeKey ltk = new LinkTimeKey(l.getFromNodeId(),j,t);
			    	double tempUf = tempLabelsMap.get(ltk);
					if(min>tempUf) {
						min = tempUf;
					}
				}
			    if(permanentLabels[j][t] - min > 1e-4) {
			    	permanentLabels[j][t] = min;
			    	finish = false;
			    }
			}
		}
	}
	
	public void setTemporaryLabels() {
		for (LinkTimeKey ltk : tempLabelsMap.keySet()) {
			
			double permanentLabel = permanentLabels[ltk.getFromNode()][ltk.getTime()];
			double travelTime = 0;
			
			if(permanentLabel != Double.MAX_VALUE) {
				
				double label = permanentLabel;
				
				TdspIntermodalLink link = null;
				
				for(Link l: graph.getNodes()[ltk.getFromNode()].getOutLinks()) {
					if(l.getToNodeId() == ltk.getToNode()) {
						link = (TdspIntermodalLink )l;
					}
				}
				
				if(link == null) {
					return;
				}
				
				if(link.getType() == "depStart") {
					
					TdspIntermodalNode fromNode = (TdspIntermodalNode)this.graph.getNodes()[ltk.getFromNode()];
					TdspIntermodalNode toNode = (TdspIntermodalNode)this.graph.getNodes()[ltk.getToNode()];
					
					switch(link.getMode()) {
					case "car":
						
						Leg leg = this.populationFactory.createLeg( "car" );
						
						Path path = containerManager.getPath(fromNode.getActivity(),toNode.getActivity() , arrivalTime[ltk.getFromNode()][ltk.getTime()],"car");
						
						if(path == null) {
							tempLabelsMap.put(ltk,Double.MAX_VALUE);
							arrivalTime[ltk.getToNode()][ltk.getTime()] = Double.MAX_VALUE;
						}
						else if(path.links.size()==0) {
							tempLabelsMap.put(ltk,0.0);
							arrivalTime[ltk.getToNode()][ltk.getTime()] = arrivalTime[ltk.getFromNode()][ltk.getTime()];
						}
						else {
							NetworkRoute route = this.populationFactory.getRouteFactories().createRoute(NetworkRoute.class, null, null);
							route.setTravelTime(path.travelTime);
							route.setTravelCost(path.travelCost);
							route.setStartLinkId(path.links.get(0).getId());
							route.setEndLinkId(path.links.get(path.links.size()-1).getId());
							List<Id<org.matsim.api.core.v01.network.Link>> linkIds = new ArrayList<>();
							for(int j =1;j<path.links.size()-1;j++) {
								linkIds.add(path.links.get(j).getId());
							}
							route.setLinkIds(path.links.get(0).getId(), linkIds, path.links.get(path.links.size()-1).getId() );
							route.setDistance(RouteUtils.calcDistance(route, 1.0, 1.0, this.network));
							leg.setRoute(route);
							leg.setTravelTime(path.travelTime);
							leg.setDepartureTime(arrivalTime[ltk.getFromNode()][ltk.getTime()]);
							
							//TODO what happens when the arrival time is out of the time window?
							travelTime = path.travelTime;
							double ll = this.scoringFunctionForPopulationGraph.getLegUtilityFunctionValueForAgent(graph.getPerson(), leg);
							label = label - ll;
							tempLabelsMap.put(ltk, label);
							arrivalTime[ltk.getToNode()][ltk.getTime()] = arrivalTime[ltk.getFromNode()][ltk.getTime()] + travelTime;
						}
						break;
						
					case "walk":
						List<? extends PlanElement> walkLegs = tripRouter.getRoutingModule("walk").calcRoute(FacilitiesUtils.toFacility(fromNode.getActivity(),facilities),
								FacilitiesUtils.toFacility(toNode.getActivity(),facilities) , arrivalTime[ltk.getFromNode()][ltk.getTime()], graph.getPerson());
						for(PlanElement l: walkLegs) {
							travelTime = travelTime + ((Leg)l).getTravelTime();
							label = label - this.scoringFunctionForPopulationGraph.getLegUtilityFunctionValueForAgent(graph.getPerson(), (Leg)l);
						}
						tempLabelsMap.put(ltk, label);
						arrivalTime[ltk.getToNode()][ltk.getTime()] = arrivalTime[ltk.getFromNode()][ltk.getTime()] + travelTime;
						break;
						
					case "bike":
                        List<? extends PlanElement> bikeLegs = tripRouter.getRoutingModule("walk").calcRoute(FacilitiesUtils.toFacility(fromNode.getActivity(),facilities),
                        		FacilitiesUtils.toFacility(toNode.getActivity(),facilities) , arrivalTime[ltk.getFromNode()][ltk.getTime()], graph.getPerson());
						for(PlanElement l: bikeLegs) {
							travelTime = travelTime + ((Leg)l).getTravelTime();
							label = label - this.scoringFunctionForPopulationGraph.getLegUtilityFunctionValueForAgent(graph.getPerson(), (Leg)l);
						}
						tempLabelsMap.put(ltk, label);
						arrivalTime[ltk.getToNode()][ltk.getTime()] = arrivalTime[ltk.getFromNode()][ltk.getTime()] + travelTime;
						break;
						
					case "ride":
					    break;
					    
					case "pt":
						break;
					default:
					
						System.out.println("");
					}
					
				}else if(link.getType() == "startEnd") {
					label = label - link.getUtility();
					tempLabelsMap.put(ltk, label);
					arrivalTime[ltk.getToNode()][ltk.getTime()] = arrivalTime[ltk.getFromNode()][ltk.getTime()] + link.getDuration();
				}
				else {
					label = permanentLabel;
					tempLabelsMap.put(ltk, label);
					arrivalTime[ltk.getToNode()][ltk.getTime()] = arrivalTime[ltk.getFromNode()][ltk.getTime()];
				}
			}
		}
	}
	
	public List<? extends PlanElement> buildSolution(GraphImpl g) {
		
		TdspIntermodalGraph graph = (TdspIntermodalGraph)g;
		
		double min = Double.MAX_VALUE;
		int minStartTime = 0;
		List<Integer> predecessorsList = new ArrayList<>(); 
		
		for(int i = 0;i<startTimes.length;i++) {
			if(min>permanentLabels[graph.getDestinationId()][i]) {
				min = permanentLabels[graph.getDestinationId()][i];
				minStartTime = i;
			}
		}
		 
		//System.out.println(permanentLabels[graph.getDestinationId()][minStartTime]);
		
		boolean rootFind = false;
		int currActivityNode = graph.getDestinationId();
		int previousActivityNode = 0;
		predecessorsList.add(graph.getDestinationId());
		
		while(!rootFind) {
			min = Double.MAX_VALUE;
			for(Link l: graph.getNodes()[currActivityNode].getInLinks()) {
				LinkTimeKey ltk = new LinkTimeKey(l.getFromNodeId(),l.getToNodeId(),minStartTime);
				if(min > tempLabelsMap.get(ltk)) {
					previousActivityNode = l.getFromNodeId();
					min = tempLabelsMap.get(ltk);
				}
			}
			
			predecessorsList.add(previousActivityNode);
			
			if(previousActivityNode == graph.getRootId()) {
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
		TdspIntermodalGraph jj = (TdspIntermodalGraph)planModel;
		//jj.print();
		init(jj);
		while(!finish) {
			setTemporaryLabels();
			setPermanentLabels();
		}
		
		return buildSolution(graph);
	}

}
