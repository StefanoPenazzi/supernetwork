/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;

/**
 * @author stefanopenazzi
 * 
 * 
 * https://www.researchgate.net/publication/239355216_A_Time-Dependent_Shortest_Path_Algorithm_for_Real-Time_Intelligent_VehicleHighway_System
 *
 */
public class ShortestPathImpl implements ShortestPath {
	
	private final TrafficDataContainer trafficDataContainer;
	private final Middlenetwork middleNetwork;

	public ShortestPathImpl(TrafficDataContainer trafficDataContainer,Middlenetwork middleNetwork) {
		this.trafficDataContainer = trafficDataContainer;
		this.middleNetwork =  middleNetwork;
	}

}
