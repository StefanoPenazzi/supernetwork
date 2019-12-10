/**
 * 
 */
package ch.ethz.matsim.supernetwork.utilities.analysis.inputData;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;

import java.util.Collection;
import java.util.List;
import java.util.HashMap;

/**
 * This class represents the object containing the analysis on the activities. 
 * These are usually used to estimate time and memory related to a certain supernetwork algorithm.
 * Different algorithms could have different activities input data even with the same plans/attributes/subpopulations.  
 * @author stefanopenazzi
 *
 */
public interface ActivitiesAnalysis {

	List<Activity> getJoinedActivities();
	HashMap<Activity,List<Activity>> getFromActivityToActivities();
	HashMap<Activity,List<Person>> getActivityPersons();
	HashMap<Activity,List<String>> getActivitySubpopulations();
	HashMap<Activity,Network> getActivitySubNetwork(); 
	
}
