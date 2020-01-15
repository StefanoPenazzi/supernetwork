/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulationData.trafficData.container;

/**
 * @author stefanopenazzi
 *
 */
public class TravelTime {
	
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

}
