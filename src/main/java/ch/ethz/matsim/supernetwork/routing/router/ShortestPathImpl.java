/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.router;

import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 * 
 * 
 * https://www.researchgate.net/publication/239355216_A_Time-Dependent_Shortest_Path_Algorithm_for_Real-Time_Intelligent_VehicleHighway_System
 *
 */
public class ShortestPathImpl implements ShortestPath {
	
	private final Middlenetwork middleNetwork;

	public ShortestPathImpl(Middlenetwork middleNetwork) {
		this.middleNetwork =  middleNetwork;
	}

}
