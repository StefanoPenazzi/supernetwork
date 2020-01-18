/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container;

import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;

/**
 * @author stefanopenazzi
 *
 */
public interface TrafficDataContainer {

	public Map<Id<Link>,List<LinkData>> getInputFlows();
	
	public Map<Id<Link>,List<LinkData>> getOutputFlows();
	
	public Map<Id<Link>, List<TravelData >> getLinksTravelTime();
	
	public void linksTravelTimeComputation();
	
	public int getLinkTravelTime(Id<Link> id, int startTime);
	
	public String printLinkTravelTime(Id<Link> id);
}
