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
public class CALNetworkRegionImpl extends ClusterActivitiesLocation {

	private List<Node> nodes = new ArrayList();
	private double area = 0;
	private double networkRadius = 0;
	private List<Double> networkRadiusArray = new ArrayList(); 
	
	public CALNetworkRegionImpl(int id,List<Activity> activities,Coord centroid){
		super(id,activities,centroid);
	}
	public CALNetworkRegionImpl(int id){
		super(id);
	}
	public List<Node> getNodes(){
		return Collections.unmodifiableList(nodes);
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
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
		setCentroid(new Coord(x,y));
	}
}
