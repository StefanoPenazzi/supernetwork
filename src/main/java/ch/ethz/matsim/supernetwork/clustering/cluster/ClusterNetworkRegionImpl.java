/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javatuples.Pair;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public class ClusterNetworkRegionImpl implements Cluster {

	private final int id;
	private List<Activity> activities = new ArrayList();
	private Coord centroid = new Coord(-1,-1);
	private List<Node> nodes = new ArrayList();
	private double area = 0;
	private double networkRadius = 0;
	private List<Double> networkRadiusArray = new ArrayList(); 
	
	public ClusterNetworkRegionImpl(int id,List<Activity> activities,Coord centroid){
		this.id = id;
		this.activities = activities;
		this.centroid = centroid;
	}
	public ClusterNetworkRegionImpl(int id){
		this.id = id;
	}
	
	public List<Activity> getActivities(){
		return Collections.unmodifiableList(activities);
	}
	public List<Node> getNodes(){
		return Collections.unmodifiableList(nodes);
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
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
	public void addActivity(Activity act) {
		activities.add(act);
	}
	public void removeActivity(Activity act) {
		activities.remove(act);
	}
	public void addNode(Node n) {
		nodes.add(n);
	}
	public double getArea() {
		return area;
	}
	public double getNetworkRadius() {
		return this.networkRadius;
	}
	public void setNetworkRadius(double radius) {
		this.networkRadius = radius;
	}
	public List<Double> getNetworkRadiusArray(){
		return Collections.unmodifiableList(this.networkRadiusArray);
	}
	public void addRadius(double r) {
		this.networkRadiusArray.add(r);
	}
	
	public void setAreaAndCentroid() {
		double x = 0;
		double y = 0;
		double area = 0;
		for(int j =0;j<this.nodes.size()-1;j++) {
			double currX = nodes.get(j).getCoord().getX();
			double currXPlusOne = nodes.get(j+1).getCoord().getX();
			double currY = nodes.get(j).getCoord().getY();
			double currYPlusOne = nodes.get(j+1).getCoord().getY();
			
			x = x + ((currX + currXPlusOne)*(currX* currYPlusOne - currXPlusOne*currY));
			y = y + ((currY + currYPlusOne)*(currX* currYPlusOne - currXPlusOne*currY));
			area = area + ((currX*currYPlusOne) - (currXPlusOne*currY));
			
		}
		area = area/2;
		x = (1/(6*area))*x;
		y = (1/(6*area))*y;
		
		this.area = Math.abs(area);
		this.centroid = new Coord(x,y);
	}
	
	public void computeCentroid() {}
}
