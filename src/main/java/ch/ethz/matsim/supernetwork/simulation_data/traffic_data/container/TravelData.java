/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container;

/**
 * @author stefanopenazzi
 *
 */
public interface TravelData extends Comparable<TravelData> {
	public int getStartTime();
	public int getTravelTime();
}
