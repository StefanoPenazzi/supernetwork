/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.Config;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.router.TripStructureUtils.Trip;
import org.matsim.core.scoring.functions.ScoringParametersForPerson;
import org.matsim.core.utils.misc.Time;
import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;


/**
 * @author stefanopenazzi
 *
 */
public class TdspIntermodalPlanModelFactory implements PlanModelFactory {

	//private final RoutingGeneralManager routingGeneralManager;
	private final ScoringParametersForPerson params;
	private final TripRouter tripRouter;
	private final ScoringFunctionsForPopulationGraph  scoringFunctionsForPopulationGraph;
	private final PlanCalcScoreConfigGroup planCalcScoreConfigGroup;
    private final PopulationFactory populationFactory;
    private final Config config;
   
    
	
    //private  List<String> modes = Arrays.asList("car","walk"); //,"bicycle","ride"
	private  List<String> modes = Arrays.asList("car","walk","bicycle","ride","pt");
	private String[] nodeType = {"actStart","actEnd","actDep"};
	private String[] linkType = {"depStart","startEnd","endDep"};
	private double timeStep = 1200;
	private double defaultRange = 3600;
	
	
	
	@Inject
	public TdspIntermodalPlanModelFactory(ScoringParametersForPerson params,TripRouter tripRouter,
			ScoringFunctionsForPopulationGraph  scoringFunctionsForPopulationGraph,PlanCalcScoreConfigGroup planCalcScoreConfigGroup,
			PopulationFactory populationFactory,Config config) { //RoutingGeneralManager routingGeneralManager,
		//this.routingGeneralManager = routingGeneralManager;
		this.params = params;
		this.tripRouter = tripRouter;
		this.scoringFunctionsForPopulationGraph = scoringFunctionsForPopulationGraph;
		this.planCalcScoreConfigGroup = planCalcScoreConfigGroup;
		this.populationFactory = populationFactory;
		this.config = config;
		
		
	}
	
	public void init() {
		this.scoringFunctionsForPopulationGraph.init();
	}
	
	public boolean check(Plan plan) {
		
//		if(TripStructureUtils.getTrips(plan, tripRouter.getStageActivityTypes()).size() == 0) {
//			return false;
//		}
		if(plan.getPlanElements().size() <= 1) {
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public TdspIntermodalGraph createPlanModel(Person person) {
		
		if(!check(person.getSelectedPlan())) return null;
		
		TdspIntermodalGraph graph = new TdspIntermodalGraph(person,convertPlanForModel(person.getSelectedPlan()));
		Plan plan = graph.getPlan();
		
		//final List<Activity> activities = TripStructureUtils.getActivities( plan , tripRouter.getStageActivityTypes() );
		
		final List<Trip> trips = TripStructureUtils.getTrips(plan, tripRouter.getStageActivityTypes());
		
		List<Activity> activities = new ArrayList<>();
		
		for(Trip trip: trips) {
			activities.add(trip.getOriginActivity());
		}
		
		activities.add(trips.get(trips.size()-1).getDestinationActivity());
		
		int idNode = 0;
		int idLink = 0;
		List<String> planModes = new ArrayList<>(modes);
		/*
		 * if(!person.getAttributes().getAttribute("carAvail").equals("always")) {
		 * planModes.remove("car"); }
		 */
		List<TdspIntermodalNode> nodesList = new ArrayList<>();
		List<List<TdspIntermodalNode>> nodesListPerPosition = new ArrayList<>();
		
		graph.setStartTimes(multiStartTimes(trips.get(0).getOriginActivity()));
		
		//NODES
		//Add first activity not considering the modes
		//TdspIntermodalNode firstNode = new TdspIntermodalNode(idNode,activities.get(0),null, nodeType[2],0,0);
		TdspIntermodalNode firstNode = new TdspIntermodalNode(idNode,trips.get(0).getOriginActivity(),null, nodeType[2],0,0);
		nodesList.add(firstNode);
		idNode++;
		List<TdspIntermodalNode> nlf = new ArrayList<>();
		nlf.add(firstNode);
		nodesListPerPosition.add(nlf);
		graph.setRootId(0);
		
		//Add intermed activities
		int position = 0;
		for(Trip trip: trips) {
			//first trip is already considered
			if(position != 0) { 
				List<TdspIntermodalNode> nl = new ArrayList<>();
				for(int k=0;k<planModes.size();++k) {
					
					
					//Activity_start node
					TdspIntermodalNode actStartNode = new TdspIntermodalNode(idNode,trip.getOriginActivity(),planModes.get(k),nodeType[0],0,position);
					nodesList.add(actStartNode);
					idNode++;
					nl.add(actStartNode);
	
					
					//Activity_end nodes
					double[] durations = activityDurations(trip.getOriginActivity());
					for(int d = 0;d<durations.length;d++) {
						TdspIntermodalNode actEndNode = new TdspIntermodalNode(idNode,null,planModes.get(k),nodeType[1],durations[d],position);
						nodesList.add(actEndNode);
						idNode++;
						nl.add(actEndNode);
					}
					
					
					//Activity_departure node
					TdspIntermodalNode actDepNode = new TdspIntermodalNode(idNode,trip.getOriginActivity(),planModes.get(k),nodeType[2],0,position);
					nodesList.add(actDepNode);
					idNode++;
					nl.add(actDepNode);
				}
				nodesListPerPosition.add(nl);
			}
			position++;
		}
		
		//Add last activity
		TdspIntermodalNode lastNode = new TdspIntermodalNode(idNode,trips.get(trips.size()-1).getDestinationActivity(),null,nodeType[0],0,trips.size()-1);
		nodesList.add(lastNode);
		List<TdspIntermodalNode> nll = new ArrayList<>();
		nll.add(lastNode);
		nodesListPerPosition.add(nll);
		graph.setDestinationId(idNode);
		
		
		
		//LINKS
		for(int j = 0; j < activities.size()-1; j++) {
			if(j == 0) {
				//Activity_departure -> Activity_start(j+1)
				for(TdspIntermodalNode n: nodesListPerPosition.get(j)) { 
					if(n.getNodeType() == nodeType[2]) {
						for(TdspIntermodalNode nn: nodesListPerPosition.get(j+1)) {
							if(nn.getNodeType() == nodeType[0]) {
								TdspIntermodalLink link = new TdspIntermodalLink(idLink,n.getId(),nn.getId(),linkType[0],nn.getMode(),0,0);
								idLink++;
								n.addOutLink(link);
								nn.addInLink(link);
								//System.out.println("");
							}
						}
					}
				}
			}
			else {
				//Activity_start -> Activity_end
				Activity linkAct = this.populationFactory.createActivityFromLinkId(activities.get(j).getType(),activities.get(j).getLinkId());
				for(TdspIntermodalNode n: nodesListPerPosition.get(j)) {
					if(n.getNodeType() == nodeType[0]) {
						for(TdspIntermodalNode nn: nodesListPerPosition.get(j)) {
							if(nn.getNodeType() == nodeType[1] && n.getMode() == nn.getMode()) {
								double uf = this.scoringFunctionsForPopulationGraph.getActivityUtilityFunctionValueForAgent(plan.getPerson(), linkAct,nn.getDuration());
								TdspIntermodalLink link = new TdspIntermodalLink(idLink,n.getId() ,nn.getId(),linkType[1],n.getMode(),nn.getDuration(),uf);
								idLink++;
								n.addOutLink(link);
								nn.addInLink(link);
							}
						}
					}
				}
				//Activity_end -> Activity_departure
				for(TdspIntermodalNode n: nodesListPerPosition.get(j)) {
					if(n.getNodeType() == nodeType[1]) {
						if(n.getMode() == "car") {
							for(TdspIntermodalNode nn: nodesListPerPosition.get(j)) {
								if(nn.getNodeType() == nodeType[2]) {
									TdspIntermodalLink link = new TdspIntermodalLink(idLink,n.getId(),nn.getId(),linkType[2],n.getMode(),0,0);
									idLink++;
									n.addOutLink(link);
									nn.addInLink(link);
								}
							}
						}
						else {
							for(TdspIntermodalNode nn: nodesListPerPosition.get(j)) {
								if(nn.getNodeType() == nodeType[2] && nn.getMode() != "car") {
									TdspIntermodalLink link = new TdspIntermodalLink(idLink,n.getId(),nn.getId(),linkType[2],n.getMode(),0,0);
									idLink++;
									n.addOutLink(link);
									nn.addInLink(link);
								}
							}
						}
					}
				}
				//Activity_departure -> Activity_start(j+1)
				for(TdspIntermodalNode n: nodesListPerPosition.get(j)) {
					if(n.getNodeType() == nodeType[2]) {
						if(j<activities.size()-2) {
							for(TdspIntermodalNode nn: nodesListPerPosition.get(j+1)) {
								if(nn.getNodeType() == nodeType[0] && n.getMode() == nn.getMode()) {
									TdspIntermodalLink link = new TdspIntermodalLink(idLink,n.getId() ,nn.getId(),linkType[0],n.getMode(),0,0);
									idLink++;
									n.addOutLink(link);
									nn.addInLink(link);
								}
							}
						}
						else {
							for(TdspIntermodalNode nn: nodesListPerPosition.get(j+1)) {
								if(nn.getNodeType() == nodeType[0]) {
									TdspIntermodalLink link = new TdspIntermodalLink(idLink,n.getId(),nn.getId(),linkType[0],n.getMode(),0,0);
									idLink++;
									n.addOutLink(link);
									nn.addInLink(link);
								}
							}
						}
					}
				}
			}
		}
		graph.buildLinksIntoNodes(nodesList);
		return graph;
	}
	
	private double[] activityDurations(Activity activity) {
		ActivityParams activityParams =  planCalcScoreConfigGroup.getActivityParams(activity.getType());
		double[] result;
		double minDuration;
		double maxDuration;
		double range;
		int steps;
		
		double startTime = (Time.isUndefinedTime(activity.getStartTime())) ? 0 : activity.getStartTime();
		double endTime = (Time.isUndefinedTime(activity.getEndTime())) ? 0 : activity.getEndTime();
		
		// single duration for the interaction
		if (activity.getType().endsWith("interaction")) {
			/*
			 * result = new double[1]; result[0] = endTime - startTime;
			 */
			throw new RuntimeException("interaction activities must not be considered as nodes of the graph");
			//return result;
		}
		
//		if(activityParams != null) {
//			if(!Time.isUndefinedTime(activityParams.getMinimalDuration())) {
//				minDuration = activityParams.getMinimalDuration();
//				if(!Time.isUndefinedTime(activity.getMaximumDuration())) {
//					maxDuration = activity.getMaximumDuration();
//				}
//				else {
//					maxDuration = minDuration+defaultRange/2;
//				}
//			}
//			else {
//				minDuration = endTime - startTime-defaultRange/2;
//				minDuration = (minDuration < 0)? 0: minDuration; 
//				
//				if(!Time.isUndefinedTime(activity.getMaximumDuration())) {
//					maxDuration = activity.getMaximumDuration();
//				}
//				else {
//					maxDuration = endTime - startTime+defaultRange/2;
//				}
//			}
//		}
//		else {
//			
//			minDuration = endTime - startTime-defaultRange/2;
//			minDuration = (minDuration < 0)? 0: minDuration; 
//			
//			if(!Time.isUndefinedTime(activity.getMaximumDuration())) {
//				minDuration = activity.getMaximumDuration()-defaultRange/2;
//				maxDuration = activity.getMaximumDuration()+defaultRange/2;
//			}
//			else {
//				maxDuration = endTime - startTime+defaultRange/2;
//			}
//		}
//		maxDuration = (maxDuration > 86400)? 86400: maxDuration; 
		
		//simp version----------------------
		double dur = config.planCalcScore().getActivityParams(activity.getType()).getTypicalDuration();
//		if(Time.isUndefinedTime(dur)) {
//			dur = endTime - startTime;
//		}
		if((dur * 0.2) > defaultRange/2) {
			maxDuration = dur+defaultRange/2;
			minDuration = dur-defaultRange/2;
			timeStep = 1200;
		}
		else {
			if(dur == 0.0) {
				maxDuration = defaultRange/2;
				minDuration = 0;
				timeStep = 600;
			}
			else
			{
				maxDuration = dur + (dur * 0.2);
				minDuration = dur - (dur * 0.2);
				timeStep = dur * 0.1;
			}
		}
		if(!Time.isUndefinedTime(config.planCalcScore().getActivityParams(activity.getType()).getMinimalDuration())) {
			if(minDuration < config.planCalcScore().getActivityParams(activity.getType()).getMinimalDuration()) {
				minDuration = activityParams.getMinimalDuration();
			}
		}
		//--------------------------------
		
		
		range = maxDuration - minDuration;
		if(range <= 0) {
			result = new double[1];
			result[0] = maxDuration;
			return result;
		}
		
		steps = (int) Math.floor(range/timeStep);
		result = new double[steps+2];
		for(int j = 0;j<=steps; ++j) {
			result[j] = minDuration + timeStep*j;
		}
		result[steps+1] = maxDuration;
		return result;
	}
	
	public double[] multiStartTimes(Activity activity) {
		
		timeStep = 3600;
		double[] res;
		double duration = 0;
		int steps = (int)Math.ceil((defaultRange/2)/timeStep);
		
		if(!Time.isUndefinedTime(activity.getEndTime())) {
			duration = activity.getEndTime();
		}
		
		if(duration == 0) {
			res = new double[steps];
			for(int i = 0;i<steps;i++) {
				res[i] = i*timeStep;
			}
		}
		else if(duration < (defaultRange/2)/timeStep ){
			res = new double[steps];
			for(int i = 0;i<steps;i++) {
				res[i] = duration + i*timeStep;
			}
		}
		else {
			res = new double[steps*2];
			duration = duration - (defaultRange/2);
			for(int i = 0;i<steps*2;i++) {
				res[i] = duration + i*timeStep;
			}
		}
		
		return res;
		
	}

	@Override
	public Plan convertPlanForModel(Plan plan) {
		
		final Plan newPlan  = PopulationUtils.createPlan(plan.getPerson());
		final List<PlanElement> newPlanElements = newPlan.getPlanElements();
		PopulationUtils.copyFromTo(plan, newPlan);
		List<Trip> trips = TripStructureUtils.getTrips(newPlan, tripRouter.getStageActivityTypes()); 
		for ( Trip trip : trips ) {
			final List<PlanElement> fullTrip =
					newPlanElements.subList(
							newPlanElements.indexOf( trip.getOriginActivity() ) + 1,
							newPlanElements.indexOf( trip.getDestinationActivity() ));
			final String mode = tripRouter.getMainModeIdentifier().identifyMainMode( fullTrip );
			fullTrip.clear();
			fullTrip.add( PopulationUtils.createLeg(mode) );
			if ( fullTrip.size() != 1 ) throw new RuntimeException( fullTrip.toString() );
		}
		return newPlan;
	}
}
