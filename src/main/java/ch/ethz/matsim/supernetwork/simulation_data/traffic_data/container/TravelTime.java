/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container;

/**
 * @author stefanopenazzi
 *
 */
public class TravelTime implements Comparable<TravelTime> {
	
	private int startTime;
	private int travelTime;
	
	public TravelTime(int startTime,int travelTime) {
		this.startTime = startTime;
		this.travelTime = travelTime;
	}
	
	public int getStartTime() {
		return this.startTime;
	}
	public int getTravelTime() {
		return this.travelTime;
	}

	@Override
	public int compareTo(TravelTime anotherTravelTime) {
		return this.getStartTime() - anotherTravelTime.getStartTime();
	}

}
