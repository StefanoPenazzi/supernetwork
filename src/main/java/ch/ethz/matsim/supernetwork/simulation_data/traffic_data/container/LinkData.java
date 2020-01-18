/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container;

import org.matsim.api.core.v01.Id;
import org.matsim.vehicles.Vehicle;

/**
 * @author stefanopenazzi
 *
 */
public interface LinkData extends InputTrafficData {
	public int getTime();
	public boolean getType();
	public Id<Vehicle> getId();
}
