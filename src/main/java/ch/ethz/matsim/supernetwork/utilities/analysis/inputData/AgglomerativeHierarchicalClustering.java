/**
 * 
 */
package ch.ethz.matsim.supernetwork.utilities.analysis.inputData;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.javatuples.Pair;
import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public class AgglomerativeHierarchicalClustering implements ActivitiesClusteringAlgo {

	private HashMap<Integer,List<Activity>> clusteringActivitiesResult =  new LinkedHashMap<>();
	private HashMap<Integer,Pair<Double,Double>> clusteringCoordinatesResult =  new LinkedHashMap<>();
	
	public AgglomerativeHierarchicalClustering() {
		
	}
	
	public HashMap<Integer,List<Activity>> getActivitiesClusteringResult(){
		return clusteringActivitiesResult;
	}
	
	public HashMap<Integer,Pair<Double,Double>> getClusteringCoordinatesResult(){
		return clusteringCoordinatesResult;
	}
}
