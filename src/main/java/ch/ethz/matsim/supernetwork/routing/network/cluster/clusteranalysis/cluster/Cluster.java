/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster;

import java.util.List;
import org.matsim.api.core.v01.Coord;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster_element.Element;

/**
 * @author stefanopenazzi
 *
 */
public interface Cluster<T extends Element> {
	
	List<T> getComponents();
	int getId();
	Coord getCentroid();
	void addComponent(T comp);
	void removeComponent(T comp);
	public void computeCentroid();
	/*
	 * public double getNetworkRadius(); public void setNetworkRadius(double
	 * radius); public List<Double> getNetworkRadiusArray(); public void
	 * addRadius(double r);
	 */

}
