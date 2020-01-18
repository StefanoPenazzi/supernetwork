/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container;

import com.google.inject.Inject;

/**
 * @author stefanopenazzi
 *
 */
public class TravelTime implements TravelData {
	
	private int startTime;
	private int travelTime;
	
	@Inject
	public TravelTime() {}
	
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
	public int compareTo(TravelData anotherTravelTime) {
		return this.getStartTime() - anotherTravelTime.getStartTime();
	}

}
