package ch.ethz.matsim.supernetwork.utilities.analysis.inputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.router.TripStructureUtils;

/**
 * This class implements the activities analysis used for the default supernetwork 
 * @author stefanopenazzi
 *
 */

public class SupNetDefaultActivitiesAnalysis implements ActivitiesAnalysis {
	
	private List<Activity> activities = new ArrayList();
	private List<Activity> joinedActivities = new ArrayList();
	private HashMap<Activity,List<Activity>> activityToActivities = new LinkedHashMap<>();
	private HashMap<Activity,List<Person>> activityPersons = new LinkedHashMap<>();
	private HashMap<Activity,List<String>> activitySubpopulation = new LinkedHashMap<>();
	private HashMap<Activity,Network> activitySubNetwork = new LinkedHashMap<>();
	
	public SupNetDefaultActivitiesAnalysis(Scenario scenario , String outputPath){
		activitiesFactory(scenario);
		joinedActivitiesFactory(scenario,outputPath);
		activityToActivitiesFactory(scenario);
		activityPersonsFactory(scenario);
		activitySubpopulationFactory(scenario);
		activitySubNetworkFactory(scenario);
	}
	
	private void activitiesFactory(Scenario scenario) {
		for(Person p: scenario.getPopulation().getPersons().values()) {
			if(p.getPlans().size() >= 1) {
				for (PlanElement pe : p.getPlans().get(0).getPlanElements()) {
					if ( !(pe instanceof Activity) ) continue;
					Activity act = (Activity) pe;
					if (act.getType() != "pt interaction" && act.getType() != "car interaction" && act.getType() != "ride interaction" 
							&& act.getType() != "bike interaction") {		
					activities.add(act);
					}
				}
			}
		}
	}
	
	private void joinedActivitiesFactory(Scenario scenario,String outputPath) {
		//Map<Id<Link>, List<Activity>> res = activities.stream().filter(x -> x.getLinkId() != null).collect(groupingBy(Activity::getLinkId));
		
		ActivitiesClusteringAlgo clusters = new RegionsPlaneClustering(scenario,outputPath);
		
		System.out.println("");
	}
	
    private void activityToActivitiesFactory (Scenario scenario) {
		
	}
    
	private void activityPersonsFactory (Scenario scenario) {
			
	}
	
	private void activitySubpopulationFactory (Scenario scenario) {
		
	}
	
    private void activitySubNetworkFactory(Scenario scenario) {
		
	}
	
	public List<Activity> getJoinedActivities(){
		return joinedActivities;
	}
	public HashMap<Activity,List<Activity>> getFromActivityToActivities(){
		return activityToActivities;
	}
	public HashMap<Activity,List<Person>> getActivityPersons(){
		return activityPersons;
	}
	public HashMap<Activity,List<String>> getActivitySubpopulations(){
		return activitySubpopulation;
	}
	public HashMap<Activity,Network> getActivitySubNetwork(){
		return activitySubNetwork;
	} 

}
