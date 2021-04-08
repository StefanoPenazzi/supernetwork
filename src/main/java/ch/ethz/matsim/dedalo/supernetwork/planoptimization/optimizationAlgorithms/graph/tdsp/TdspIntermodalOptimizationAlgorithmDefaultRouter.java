/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.graph.tdsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.controler.Controler;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;
import org.matsim.facilities.Facility;

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
public class TdspIntermodalOptimizationAlgorithmDefaultRouter extends OrdaRomOptimizationAlgorithm {
	
	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	//private final PopulationFactory populationFactory;
	//private final Network network;
	private final TripRouter tripRouter;
	private final ActivityFacilities facilities;
	//private final ActivityManager activityManager;
	
	private double[] startTimes;
	private double[][] arrivalTime;
	private double[][] permanentLabels;
	private SortedMap<LinkTimeKey,UfTime> tempLabelsMap;
	private TdspIntermodalGraph graph;
	private boolean finish = false;
	
	boolean notFoundPath = false;
	
	
	double routingTimeCar;
	double routingTimeRide = 0;
    double routingTimePt = 0;
	double routingTimeOthers = 0;
	double countCar = 0;
	double countPt = 0;
	double countOthers = 0;
	double countRide = 0;
	 
	 
	private CacheTripsMap cacheTripsMap; 
	
	private static final Logger log = Logger.getLogger(TdspIntermodalOptimizationAlgorithmDefaultRouter.class);
	
	
	public TdspIntermodalOptimizationAlgorithmDefaultRouter(ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,PopulationFactory populationFactory
			,Network network,TripRouter tripRouter,ActivityFacilities facilities,ActivityManager activityManager) {
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		//this.populationFactory = populationFactory;
		//this.network = network;
		this.tripRouter = tripRouter;
		this.facilities = facilities;
		//this.activityManager = activityManager;
	}
	
	
	public void init(GraphImpl g) {
		
	     routingTimeCar = 0;
		 routingTimePt = 0;
		 routingTimeOthers = 0;
		 countCar = 0;
		 countPt = 0;
		 countOthers = 0;
		
		cacheTripsMap = new CacheTripsMap();
		
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
					
					Facility fromFracility = FacilitiesUtils.toFacility(fromNode.getActivity(), facilities );
					Facility toFracility = FacilitiesUtils.toFacility(toNode.getActivity(), facilities );
					//TODO why sometimes mode is = null?
					String mode = link.getMode() != null?  link.getMode() : "walk";
					
					double depTime = arrivalTime[ltk.getFromNode()][ltk.getTime()];
					
					//departure times after 24h are considered infinite
					if(depTime >= 86000) {
						tempLabelsMap.put(ltk, new UfTime(Double.MAX_VALUE,Double.MAX_VALUE));
						continue;
					}
					
					CacheTripKey cacheTrip = null;
					UfTime cacheUft = null;
					
					/*
					 * try {
					 */
						cacheTrip = new CacheTripKey(fromFracility,toFracility,mode);
						cacheUft = cacheTripsMap.get(cacheTrip, depTime);
						
                    /*}catch(NullPointerException e) {
						log.error("errore nella chiave:");
						log.error("fromfacility = : " + fromFracility.getLinkId().toString());
						log.error("tofacility = : " + toFracility.getLinkId().toString());
						log.error("mode : "+ mode);
					}*/
						
					if(cacheUft != null) {
						travelTime = travelTime + cacheUft.time ;
						label = label + cacheUft.uf;
					}
					else {
						
						List<? extends PlanElement> newTrip = null;
						try {
							long start = System.nanoTime();
							newTrip =
								tripRouter.calcRoute(
										mode,
										fromFracility,
										toFracility,
										depTime,
									    graph.getPerson());
							long fin = System.nanoTime();
							long timeElapsed = fin - start;
							
							switch (mode) {
							case "car":
								routingTimeCar += ((double)timeElapsed/1000000);
								countCar++;
								break;
							case "pt":
								routingTimePt += ((double)timeElapsed/1000000);
								countPt++;
								break;
							case "ride":
								routingTimeRide += ((double)timeElapsed/1000000);
								countRide++;
							    break;
							case "bicycle":
								routingTimeOthers += ((double)timeElapsed/1000000);
								countOthers++;
								break;
							case "walk":
								routingTimeOthers += ((double)timeElapsed/1000000);
								countOthers++;;
								break;
							
							}
							
						
						  } catch (ArrayIndexOutOfBoundsException e) {
						  
						  log.error("Out of time in optimization : person id = "+
						  graph.getPerson().getId().toString() ); e.printStackTrace();
						  tempLabelsMap.put(ltk, new UfTime(Double.MAX_VALUE,Double.MAX_VALUE));
						  continue; }
						
						
						
						double linkTravelTime = 0;
						double labelLink = 0;
						
						for(PlanElement l: newTrip) {
							if(l instanceof Leg ) {
								linkTravelTime = linkTravelTime + ((Leg)l).getTravelTime();
								labelLink = labelLink - this.scoringFunctionForPopulationGraph.getLegUtilityFunctionValueForAgent(graph.getPerson(), (Leg)l);
							}
						}
						
						cacheTripsMap.put(cacheTrip,depTime,new UfTime(labelLink,linkTravelTime));
						travelTime = travelTime + linkTravelTime;
						label = label + labelLink;
					}
					tempLabelsMap.put(ltk, new UfTime(label,depTime + travelTime));
					
				}else if(link.getType() == "startEnd") {
					label = label - link.getUtility();
					tempLabelsMap.put(ltk, new UfTime(label,arrivalTime[ltk.getFromNode()][ltk.getTime()] + link.getDuration()));
				
				}
				else {
					label = permanentLabel + link.getUtility(); 
					tempLabelsMap.put(ltk, new UfTime(label,arrivalTime[ltk.getFromNode()][ltk.getTime()]));
					
				}
			}
		}
	}
	
	public Plan buildSolution(GraphImpl g) {
		
		/*
		 * //I should use the RoutingModules
		 * if(g.getPerson().getId().toString().equals("10000001")) {
		 * System.out.println(); }
		 */
		
		double fTime = 86400;
		double sTime = 0;
		Activity nextActivity=null;
		double nextActivityArrivalTime = 0;
		int legCounter = 1;
		
		TdspIntermodalGraph graph = (TdspIntermodalGraph)g;
		
		OptimalSolution optimalSolution =  optimalPath(graph);
		if (optimalSolution == null) return null;
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
			if(mode == null) return null;
			
			final List<? extends PlanElement> newTrip =
					tripRouter.calcRoute(
							mode,
						  FacilitiesUtils.toFacility(activities.get(index), facilities ),
						  FacilitiesUtils.toFacility(nextActivity, facilities ),
						  depTime,
							plan.getPerson());
			trips.add(0,newTrip);
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
		if(mode == null) return null;
		final List<? extends PlanElement> newTrip =
				tripRouter.calcRoute(
						mode,
					  FacilitiesUtils.toFacility(activities.get(index), facilities ),
					  FacilitiesUtils.toFacility(nextActivity, facilities ),
					  startTimes[optimalSolution.startTime],
						plan.getPerson());
		trips.add(0,newTrip);
		
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
		
		return newPlan;
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
		
		if(min == Double.MAX_VALUE || arrivalTime[graph.getDestinationId()][minStartTime] >86400) {
			return null;
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
	public Plan run(PlanModel planModel) {
		TdspIntermodalGraph jj = (TdspIntermodalGraph)planModel;
		//jj.print();
		init(jj);
		
		//long start = System.nanoTime();
		while(!finish) {
			setTemporaryLabels();
			setPermanentLabels();
			
		}
		//long finish = System.nanoTime();
		//long timeElapsed = finish - start;
		//System.out.printf(" exe time : %f", ((double)timeElapsed/1000000));
		//System.out.println("");
		
		
//		  log.error("routing time agent id = "+
//		  planModel.getPerson().getId().toString() + " is CarTime = " + routingTimeCar
//		  + " CarCount = " + countCar + " is PtTime = " + routingTimePt + " PtCount = "
//		  + countPt + " is RideTime = " + routingTimeRide + " RideCount = " + countRide
//		  + " is OthersTime = " + routingTimeOthers + " OthersCount = " + countOthers
//		  );
		
//		
//		log.error("algotime;"+planModel.getPerson().getId().toString() + "," + routingTimeCar
//				  + "," + countCar + "," + routingTimePt + ","+ countPt + "," + routingTimeRide + "," + countRide
//				  + "," + routingTimeOthers + "," + countOthers
//				  );
		 
		
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
	
	private class CacheTripsMap{
		private double error = 600;
		private Map<CacheTripKey, NavigableMap<Double,UfTime>> cacheTripsMap = new HashMap<CacheTripKey, NavigableMap<Double,UfTime>>();
		public void put(CacheTripKey ctk, double depTime, UfTime uft) {
			if(cacheTripsMap.containsKey(ctk)) {
				cacheTripsMap.get(ctk).put(depTime,uft);
			}
			else {
				NavigableMap<Double,UfTime> map = new TreeMap<Double,UfTime>();
				map.put(depTime,uft);
				cacheTripsMap.put(ctk, map);
			}
		}
		public UfTime get(CacheTripKey ctk, double depTime) {
			if(cacheTripsMap.containsKey(ctk)) {
				NavigableMap<Double,UfTime> map = cacheTripsMap.get(ctk);
				Map.Entry<Double,UfTime> res = map.floorEntry(depTime);
				if(res == null) return null;
				if(depTime >= res.getKey() - error && depTime <= res.getKey() + error ) {
					return res.getValue();
				}
				else {
					return null;
				}
			}
			else {
				return null;
			}
		}
		
	}
	
	private class CacheTripKey{
		private final Facility fromFacility;
		private final Facility toFacility;
		private final String mode;
		
		public CacheTripKey(Facility fromFacility, Facility toFacility, 
				String mode) {
			this.fromFacility = fromFacility;
			this.toFacility = toFacility;
			this.mode = mode;
	
			
		}
		
		@Override
	    public boolean equals(Object o) {
			if(this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheTripKey ct  = (CacheTripKey)o;
			if(this.fromFacility.getLinkId().toString().equals(ct.fromFacility.getLinkId().toString())
					&& this.toFacility.getLinkId().toString().equals(ct.toFacility.getLinkId().toString()) &&
					this.mode.equals(mode)) {
				return true;
			}
			else {return false;}
			
		}
		@Override    
	    public int hashCode() {
			String s1 = this.fromFacility.getLinkId().toString();
			String s2 = this.toFacility.getLinkId().toString();
			s1 = s1 != null? s1 : "0";
			s2 = s2 != null? s2 : "0";
			return s1.hashCode() * s2.hashCode() *
					this.mode.hashCode();
			/*
			 * int result = (fromFacility != null ? fromFacility.hashCode() : 0); result =
			 * 31 * result + (toFacility != null ? toFacility.hashCode() : 0); result = 31 *
			 * result + (mode != null ? mode.hashCode() : 0); return result;
			 */
		}
		
		public void print() {
			System.out.println("--------------");
			System.out.println("FromFacility : "+ fromFacility.getLinkId() + " - ToFacility : "+ toFacility.getLinkId() + " - Mode  : "+ mode);
			System.out.println(" >>>> hashCode : " + this.hashCode());
			System.out.println("--------------");
		} 
	}
}
