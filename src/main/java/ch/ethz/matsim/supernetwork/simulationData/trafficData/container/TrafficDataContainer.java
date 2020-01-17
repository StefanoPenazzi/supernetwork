/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulationData.trafficData.container;

import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;

/**
 * @author stefanopenazzi
 *
 */
public interface TrafficDataContainer {

	public Map<Id<Link>,List<InOutTime>> getInputFlows();
	
	public Map<Id<Link>,List<InOutTime>> getOutputFlows();
	
	public Map<Id<Link>, List<TravelTime>> getLinksTravelTime();
	
	public void linksTravelTimeComputation();
	
	public int getLinkTravelTime(Id<Link> id, int startTime);
	
	public String printLinkTravelTime(Id<Link> id);
}
