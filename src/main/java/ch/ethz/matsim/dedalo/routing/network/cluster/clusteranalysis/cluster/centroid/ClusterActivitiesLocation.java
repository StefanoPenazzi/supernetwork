/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster.centroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.matsim.api.core.v01.Coord;

import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster.Cluster;
import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;

/**
 * @author stefanopenazzi
 *
 */
public class ClusterActivitiesLocation implements Cluster<ElementActivity>{
	
	private final int id;
	private List<ElementActivity> activities  = new ArrayList();
	private Coord centroid;
	
	public ClusterActivitiesLocation(int id,List<ElementActivity> activities,Coord centroid){
		this.id = id;
		this.activities = activities;
		this.centroid = centroid;
	}
	
	public ClusterActivitiesLocation(int id){
		this.id = id;
	}
	
	public List<ElementActivity> getComponents(){
		return Collections.unmodifiableList(activities);
	}
	public int getId() {
		return this.id;
	}
	public Coord getCentroid() {
		return this.centroid;
	}
	public void setCentroid(Coord centroid) {
		this.centroid = centroid;
	}
	public void addComponent(ElementActivity act) {
		activities.add(act);
	}
	public void removeComponent(ElementActivity act) {
		activities.remove(act);
	}
	public void computeCentroid() {
		double x = 0;
		double y = 0;
		for(ElementActivity act:activities) {
			x += act.getFacility().getCoord().getX();
			y += act.getFacility().getCoord().getY();
		}
		x = x/activities.size();
		y = y/activities.size();
		this.centroid = new Coord(x,y);
	}
	
	public double variance() {
		double var = 0;
		for(ElementActivity act:activities) {
			var += Math.sqrt(Math.pow(act.getFacility().getCoord().getX() - centroid.getX(), 2) + Math.pow(act.getFacility().getCoord().getY() - centroid.getY(), 2));
		}
		var = var/activities.size();
		return var;
	}
	
	public double maxDistAct() {
		double max = 0;
		for(ElementActivity act:activities) {
			double dist = Math.sqrt(Math.pow(act.getFacility().getCoord().getX() - centroid.getX(), 2) + Math.pow(act.getFacility().getCoord().getY() - centroid.getY(), 2));
			if(max < dist) {
				max = dist;
			}
		}
		return max;
	}
	
	public double minDistAct() {
		double min = Double.MAX_VALUE;
		for(ElementActivity act:activities) {
			double dist = Math.sqrt(Math.pow(act.getFacility().getCoord().getX() - centroid.getX(), 2) + Math.pow(act.getFacility().getCoord().getY() - centroid.getY(), 2));
			if(min > dist) {
				min = dist;
			}
		}
		return min;
	}
	
	public void sortActivitiesByCentroidDistNextAct() {
		Collections.sort(activities, new Comparator<ElementActivity>() {
			@Override
			public int compare(ElementActivity e1, ElementActivity e2) {
				double dist1; 
				double dist2; 
				
				if(e1.getNextFacility() != null) { 
					dist1 = Math.pow(e1.getNextFacility().getCoord().getX() - centroid.getX(), 2)+
							Math.pow(e1.getNextFacility().getCoord().getY() - centroid.getY(), 2);
				}
				else {
					dist1 = Double.MAX_VALUE;
				}
				if(e2.getNextFacility() != null) { 
					dist2 =Math.pow(e2.getNextFacility().getCoord().getX() - centroid.getX(), 2)+
							Math.pow(e2.getNextFacility().getCoord().getY() - centroid.getY(), 2);
				}
				else {
					dist2 = Double.MAX_VALUE;
				}
				
				if(dist1 == dist2) {
					return 0;
				}
				else { 
					if(dist1 < dist2) {
				  
					return -1;
					}
					else {
						return 1;
					}
			    }
			}
		});
	}
	
	public void printActivitiesAndDist() {
		int i =0;
		for(ElementActivity ea: activities) {
			if(ea.getNextFacility() != null) {
				double dist = Math.pow(ea.getNextFacility().getCoord().getX() - centroid.getX(), 2)+
						Math.pow(ea.getNextFacility().getCoord().getY() - centroid.getY(), 2);
				System.out.println(i + " - " + dist);
				++i;
			}
		}
	}

}
