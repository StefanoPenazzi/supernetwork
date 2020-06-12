/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scoring.functions.ScoringParametersForPerson;
import org.matsim.core.utils.misc.Time;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Graph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.NodeImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.replanning.supernetwork.SupernetworkModel;

/**
 * @author stefanopenazzi
 *
 */
public class TdspOrdaRom implements SupernetworkModel {

	private final ContainerManager containerManager;
	private final ScoringParametersForPerson params;
	private final TripRouter tripRouter;
	private final ScoringFunctionsForPopulationGraph  scoringFunctionsForPopulationGraph;
	private final PlanCalcScoreConfigGroup planCalcScoreConfigGroup;
    private final PopulationFactory populationFactory;
	
	
	private  List<String> modes = Arrays.asList("car","walk","bike");
	private double timeStep = 5;
	private double defaultRange = 3600;
	
	
	
	@Inject
	public TdspOrdaRom(ContainerManager containerManager,ScoringParametersForPerson params,TripRouter tripRouter,
			ScoringFunctionsForPopulationGraph  scoringFunctionsForPopulationGraph,PlanCalcScoreConfigGroup planCalcScoreConfigGroup,
			PopulationFactory populationFactory) {
		this.containerManager = containerManager;
		this.params = params;
		this.tripRouter = tripRouter;
		this.scoringFunctionsForPopulationGraph = scoringFunctionsForPopulationGraph;
		this.planCalcScoreConfigGroup = planCalcScoreConfigGroup;
		this.populationFactory = populationFactory;
		
	}
	
	public void init() {
		this.scoringFunctionsForPopulationGraph.init();
	}
	
	@Override
	public List<? extends PlanElement> newPlan(Plan plan) {
		// create the graph from the plan
		
		//find the optimal solution
		return null;
	}
	
	//this should be build only one time.
	private Graph createGraph(Plan plan) {
		Graph graph = new GraphImpl();
		final List<Activity> activities = TripStructureUtils.getActivities( plan , tripRouter.getStageActivityTypes() );
		int idNode = 0;
		int idLink = 0;
		List<String> planModes = new ArrayList<>(modes);
		if(plan.getPerson().getAttributes().getAttribute("car_avail") != "always") {
			planModes.remove("car");
		}
		
		for(int j =0; j< activities.size(); j++) {
			Activity act = activities.get(j);
			Activity nextAct = (j<activities.size()-1)? activities.get(j+1):null;
			if(nextAct != null) {
				if (nextAct.getType().endsWith("interaction")) {
					System.out.println("");
				}
			}
			TdspNode n = new TdspNode(idNode);
			
			//LINKS
			//for the activity durations
			double[] durations = activityDurations(act);
			for(int i =0;i<durations.length;i++) {
				//for all the mode
				for(int k=0;k<planModes.size();++k) {
					
					//TODO LINKS
					//TdspLink link = new TdspLink(idLink,idNode,idNode+1,act,);
					idLink++;
				}
			}
			
			graph.addNode(n);
			idNode++;
		}
		graph.build();
		return graph;
	}

	private double[] activityDurations(Activity activity) {
		ActivityParams activityParams =  planCalcScoreConfigGroup.getActivityParams(activity.getType());
		double[] result;
		double minDuration;
		double maxDuration;
		double range;
		int steps;
		
		// single duration for the interaction
		if (activity.getType().endsWith("interaction")) {
			result = new double[1];
			result[0] = activity.getEndTime() - activity.getStartTime();
			return result;
		}
		
		if(!Time.isUndefinedTime(activityParams.getMinimalDuration())) {
			minDuration = activityParams.getMinimalDuration();
			if(!Time.isUndefinedTime(activity.getMaximumDuration())) {
				maxDuration = activityParams.getMinimalDuration();
			}
			else {
				maxDuration = activity.getEndTime() - activity.getStartTime()+defaultRange/2;
			}
		}
		else {
			
			minDuration = activity.getEndTime() - activity.getStartTime()-defaultRange/2;
			minDuration = (minDuration < 0)? 0: minDuration; 
			
			if(!Time.isUndefinedTime(activity.getMaximumDuration())) {
				maxDuration = activityParams.getMinimalDuration();
			}
			else {
				maxDuration = activity.getEndTime() - activity.getStartTime()+defaultRange/2;
			}
		}
		maxDuration = (maxDuration > 86400)? 86400: maxDuration; 
		
		range = maxDuration - minDuration;
		steps = (int) Math.floor(range/timeStep);
		
		result = new double[steps+2];
		
		for(int j = 0;j<=steps; ++j) {
			result[j] = minDuration + timeStep*j;
		}
		result[steps+1] = maxDuration;
		return result;
	}
	
}
