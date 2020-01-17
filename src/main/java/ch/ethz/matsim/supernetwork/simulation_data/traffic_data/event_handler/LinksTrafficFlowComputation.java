/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.controler.events.IterationEndsEvent;
import org.matsim.core.controler.listener.IterationEndsListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkDataTTV;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;

/**
 * @author stefanopenazzi
 *
 */
public final class LinksTrafficFlowComputation implements IterationEndsListener {

	private TrafficDataContainer container;
	
	@Inject
	public LinksTrafficFlowComputation (TrafficDataContainer container) {
		this.container = container;
	}
	
	@Override
	public void notifyIterationEnds(IterationEndsEvent event) {
		this.container.linksTravelTimeComputation();
		for(Id<Link> id: container.getInputFlows().keySet()) {
			System.out.println(this.container.printLinkTravelTime(id));
		}	
	}
}
