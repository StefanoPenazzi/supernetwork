/**
 * 
 */
package ch.ethz.matsim.supernetwork.utilities.analysis.inputData;

import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;
import org.matsim.api.core.v01.population.Activity;

/**
 * Clustering algorithms for the activities grouping in the supernetwork 
 * @author stefanopenazzi
 *
 */
public interface ActivitiesClusteringAlgo {
	
	HashMap<Integer,List<Activity>> getActivitiesClusteringResult(); 
	HashMap<Integer,Pair<Double,Double>> getClusteringCoordinatesResult();

}
