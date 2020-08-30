/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.graph.tdsp;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.population.routes.RouteUtils;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModel;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Node;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal.TdspIntermodalLink;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal.TdspIntermodalNode;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManager;

/**
 * @author stefanopenazzi
 *
 */
public class TdspIntermodalOptimizationAlgorithm extends OrdaRomOptimizationAlgorithm {
	
	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	private final RoutingGeneralManager routingGeneralManager;
	private final PopulationFactory populationFactory;
	private final Network network;
	private final TripRouter tripRouter;
	private final ActivityFacilities facilities;
	private final ActivityManager activityManager;
	
	private double[] startTimes;
	private double[][] arrivalTime;
	private double[][] permanentLabels;
	private SortedMap<LinkTimeKey,UfTime> tempLabelsMap;
	private TdspIntermodalGraph graph;
	private boolean finish = false;
	
	boolean notFoundPath = false;
	
	
	public TdspIntermodalOptimizationAlgorithm(ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,RoutingGeneralManager routingGeneralManager,PopulationFactory populationFactory
			,Network network,TripRouter tripRouter,ActivityFacilities facilities,ActivityManager activityManager) {
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		this.routingGeneralManager = routingGeneralManager;
		this.populationFactory = populationFactory;
		this.network = network;
		this.tripRouter = tripRouter;
		this.facilities = facilities;
		this.activityManager = activityManager;
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
		
		tempLabelsMap = new TreeMap<LinkTimeKey,UfTime>();
		for(Node n: graph.getNodes()) {
			for(Link l: n.getOutLinks()) {
				for(int k = 0;k<startTimes.length;k++) {
					LinkTimeKey lk = new LinkTimeKey(l.getFromNodeId(),l.getToNodeId(),k);
					tempLabelsMap.put(lk, new UfTime(Double.MAX_VALUE,Double.MAX_VALUE));
				}
			}
		}
		
		for(int i =0;i<this.startTimes.length;i++) {
			arrivalTime[graph.getRootId()][i] = this.startTimes[i];
			permanentLabels[graph.getRootId()][i] = 0;
		}
		notFoundPath = false;
	}
	
	public void setPermanentLabels() {
		finish = true;
		for(int j = 1;j<graph.getNodes().length;j++) {
			for(int t = 0;t<startTimes.length;t++) {
				double minUf = Double.MAX_VALUE;
				double minTime = Double.MAX_VALUE;
			    for(Link l: graph.getNodes()[j].getInLinks()) {
			    	LinkTimeKey ltk = new LinkTimeKey(l.getFromNodeId(),j,t);
			    	double tempUf = tempLabelsMap.get(ltk).uf;
			    	double tempTime = tempLabelsMap.get(ltk).time;
					if(minUf>tempUf) {
						minUf = tempUf;
						minTime = tempTime;
					}
				}
			    if(permanentLabels[j][t] - minUf > 1e-4) {
			    	permanentLabels[j][t] = minUf;
			    	arrivalTime[j][t] = minTime;
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
						
						Path path = routingGeneralManager.getPath(activityManager.ActivityToNode(fromNode.getActivity()),
								activityManager.ActivityFromNode(toNode.getActivity()) , arrivalTime[ltk.getFromNode()][ltk.getTime()],"car");
						
						if(path == null || path.links.size()==0) {
							tempLabelsMap.put(ltk,new UfTime(Double.MAX_VALUE,Double.MAX_VALUE));
							notFoundPath = true;
							
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
							tempLabelsMap.put(ltk, new UfTime(label,arrivalTime[ltk.getFromNode()][ltk.getTime()] + travelTime));
							
						}
						break;
						
					case "walk":
						List<? extends PlanElement> walkLegs = tripRouter.getRoutingModule("walk").calcRoute(FacilitiesUtils.toFacility(fromNode.getActivity(),facilities),
								FacilitiesUtils.toFacility(toNode.getActivity(),facilities) , arrivalTime[ltk.getFromNode()][ltk.getTime()], graph.getPerson());
						for(PlanElement l: walkLegs) {
							travelTime = travelTime + ((Leg)l).getTravelTime();
							label = label - this.scoringFunctionForPopulationGraph.getLegUtilityFunctionValueForAgent(graph.getPerson(), (Leg)l);
						}
						tempLabelsMap.put(ltk, new UfTime(label,arrivalTime[ltk.getFromNode()][ltk.getTime()] + travelTime));
					
						break;
						
					case "bike":
                        List<? extends PlanElement> bikeLegs = tripRouter.getRoutingModule("walk").calcRoute(FacilitiesUtils.toFacility(fromNode.getActivity(),facilities),
                        		FacilitiesUtils.toFacility(toNode.getActivity(),facilities) , arrivalTime[ltk.getFromNode()][ltk.getTime()], graph.getPerson());
						for(PlanElement l: bikeLegs) {
							travelTime = travelTime + ((Leg)l).getTravelTime();
							label = label - this.scoringFunctionForPopulationGraph.getLegUtilityFunctionValueForAgent(graph.getPerson(), (Leg)l);
						}
						tempLabelsMap.put(ltk, new UfTime(label,arrivalTime[ltk.getFromNode()][ltk.getTime()] + travelTime));
						
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
					tempLabelsMap.put(ltk, new UfTime(label,arrivalTime[ltk.getFromNode()][ltk.getTime()] + link.getDuration()));
				
				}
				else {
					label = permanentLabel;
					tempLabelsMap.put(ltk, new UfTime(label,arrivalTime[ltk.getFromNode()][ltk.getTime()]));
					
				}
			}
		}
	}
	
	public boolean buildSolution(GraphImpl g) {
		
		//I should use the RoutingModules
		
		double fTime = 86400;
		double sTime = 0;
		Activity nextActivity=null;
		double nextActivityArrivalTime = 0;
		int legCounter = 1;
		
		TdspIntermodalGraph graph = (TdspIntermodalGraph)g;
		
		OptimalSolution optimalSolution =  optimalPath(graph);
		int optStartTime = optimalSolution.startTime;
		List<Integer> optPath = optimalSolution.path;
		
		Plan plan = graph.getPlan();
		
		//first node in the predecessorList is always the last activity. The endTime of the last activity is 11.59pm
		List<Activity> activities = TripStructureUtils.getActivities( plan , tripRouter.getStageActivityTypes() );
		List<List<? extends PlanElement>> trips = new ArrayList<>();
	
		//last activitiy home
		int index = activities.indexOf(((TdspIntermodalNode)graph.getNodes()[optPath.get(0)]).getActivity());
		double arrTime = arrivalTime[optPath.get(0)][optStartTime];
		activities.get(index).setStartTime(arrTime);
		activities.get(index).setMaximumDuration(fTime-arrTime);
		activities.get(index).setEndTime(fTime);
		nextActivity = activities.get(index);
		nextActivityArrivalTime = arrTime;
		
		for(int i = 1;i<optimalSolution.path.size()-2;i+=3) {
			
			//Activity
			index = activities.indexOf(((TdspIntermodalNode)graph.getNodes()[optimalSolution.path.get(i)]).getActivity());
			arrTime = arrivalTime[optimalSolution.path.get(i+2)][optimalSolution.startTime];
			double depTime = arrivalTime[optimalSolution.path.get(i)][optimalSolution.startTime];
			activities.get(index).setStartTime(arrTime);
			activities.get(index).setMaximumDuration(depTime-arrTime);
			activities.get(index).setEndTime(depTime);
			
			String mode = ((TdspIntermodalNode)graph.getNodes()[optimalSolution.path.get(i)]).getMode();
			
			
			final List<? extends PlanElement> newTrip =
					tripRouter.calcRoute(
							mode,
						  FacilitiesUtils.toFacility(activities.get(index), facilities ),
						  FacilitiesUtils.toFacility(nextActivity, facilities ),
						  depTime,
							plan.getPerson());
			trips.add(newTrip);
			nextActivity = activities.get(index);
			nextActivityArrivalTime = arrTime;
			//legCounter++;
			
		}
		
		//first activitiy home
		index = activities.indexOf(((TdspIntermodalNode)graph.getNodes()[optimalSolution.path.get(optimalSolution.path.size()-1)]).getActivity());
		activities.get(index).setStartTime(sTime);
		activities.get(index).setMaximumDuration(startTimes[optimalSolution.startTime]-sTime);
		activities.get(index).setEndTime(startTimes[optimalSolution.startTime]);
		//first leg
		String mode = ((TdspIntermodalNode)graph.getNodes()[optimalSolution.path.get(optimalSolution.path.size()-2)]).getMode();
		final List<? extends PlanElement> newTrip =
				tripRouter.calcRoute(
						mode,
					  FacilitiesUtils.toFacility(activities.get(index), facilities ),
					  FacilitiesUtils.toFacility(nextActivity, facilities ),
					  startTimes[optimalSolution.startTime],
						plan.getPerson());
		trips.add(newTrip);
		
		//newPlan
		final Plan newPlan  = PopulationUtils.createPlan(plan.getPerson());
		for(int i =0;i<activities.size()-1;i++) {
			newPlan.addActivity(activities.get(i));
			for(PlanElement pe: trips.get(i)) {
				if(pe instanceof Activity) {
					newPlan.addActivity((Activity)pe);
				}
				else {
					newPlan.addLeg((Leg)pe);
				}
			}
		}
		newPlan.addActivity(activities.get(activities.size()-1));
		
		return notFoundPath;
	}
	
	public OptimalSolution optimalPath(TdspIntermodalGraph graph){
		
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
				if(min > tempLabelsMap.get(ltk).uf) {
					previousActivityNode = l.getFromNodeId();
					min = tempLabelsMap.get(ltk).uf;
				}
			}
			
			predecessorsList.add(previousActivityNode);
			
			if(previousActivityNode == graph.getRootId()) {
				return new OptimalSolution(minStartTime,predecessorsList);
			}
			else {
				currActivityNode = previousActivityNode;
			}
		}
		return null;
	}
	
	@Override
	public boolean run(PlanModel planModel) {
		TdspIntermodalGraph jj = (TdspIntermodalGraph)planModel;
		//jj.print();
		init(jj);
		long start = System.nanoTime();
		while(!finish) {
			setTemporaryLabels();
			setPermanentLabels();
		}
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		System.out.printf(" exe time : %f", ((double)timeElapsed/1000000));
		System.out.println("");
		
		return buildSolution(graph);
	}
	
	public class OptimalSolution {
		
		public int startTime;
		public List<Integer> path;
		
		public OptimalSolution(int startTime,List<Integer> path) {
			this.startTime = startTime;
			this.path = path;
		}
	}

	public class UfTime{
		
		public double uf;
		public double time;
		
		public UfTime(double uf,double time) {
			this.uf = uf;
			this.time = time;
		}
	}
}
