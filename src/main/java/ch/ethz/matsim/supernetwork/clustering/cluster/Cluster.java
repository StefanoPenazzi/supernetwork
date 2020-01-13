/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.cluster;

import java.util.Collections;
import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public interface Cluster<T> {
	
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
