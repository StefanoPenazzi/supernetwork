/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster.centroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;




/**
 * @author stefanopenazzi
 *
 */
public class CALDefaultImpl extends ClusterActivitiesLocation {
	
	private double networkRadius = 0;
	
	public CALDefaultImpl(int id,List<ElementActivity> activities,Coord centroid){
		super(id,activities,centroid);
	}
	public CALDefaultImpl(int id){
		super(id);
	}
}
