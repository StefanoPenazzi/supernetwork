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

	public Map<Id<Link>,List<Integer>> getInputFlows();
	
	public Map<Id<Link>,List<Integer>> getOutputFlows();
	
	public Map<Id<Link>,TravelTime[]> getLinksTravelTime();
	
	public void linksTravelTimeComputation();
}
