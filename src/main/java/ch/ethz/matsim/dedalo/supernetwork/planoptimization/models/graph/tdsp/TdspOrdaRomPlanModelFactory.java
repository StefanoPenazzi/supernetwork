/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdsp;

import java.util.Arrays;
import java.util.List;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.router.TripRouter;
import org.matsim.core.scoring.functions.ScoringParametersForPerson;
import org.matsim.core.utils.misc.Time;
import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.manager.ContainerManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModel;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;

/**
 * @author stefanopenazzi
 *
 */
public class TdspOrdaRomPlanModelFactory implements PlanModelFactory {

	private final ContainerManager containerManager;
	private final ScoringParametersForPerson params;
	private final TripRouter tripRouter;
	private final ScoringFunctionsForPopulationGraph  scoringFunctionsForPopulationGraph;
	private final PlanCalcScoreConfigGroup planCalcScoreConfigGroup;
    private final PopulationFactory populationFactory;
    
	
	
	private  List<String> modes = Arrays.asList("car","walk","bike");
	private double timeStep = 1200;
	private double defaultRange = 3600;
	
	
	
	@Inject
	public TdspOrdaRomPlanModelFactory(ContainerManager containerManager,ScoringParametersForPerson params,TripRouter tripRouter,
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
			result = new double[1];
			result[0] = endTime - startTime;
			return result;
		}
		
		if(!Time.isUndefinedTime(activityParams.getMinimalDuration())) {
			minDuration = activityParams.getMinimalDuration();
			if(!Time.isUndefinedTime(activity.getMaximumDuration())) {
				maxDuration = activity.getMaximumDuration();
			}
			else {
				maxDuration = endTime - startTime+defaultRange/2;
			}
		}
		else {
			minDuration = endTime - startTime-defaultRange/2;
			minDuration = (minDuration < 0)? 0: minDuration; 
			
			if(!Time.isUndefinedTime(activity.getMaximumDuration())) {
				maxDuration = activity.getMaximumDuration();
			}
			else {
				maxDuration = endTime - startTime+defaultRange/2;
			}
		}
		maxDuration = (maxDuration > 86400)? 86400: maxDuration; 
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

	@Override
	public PlanModel createPlanModel(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plan convertPlanForModel(Plan plan) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
