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
import ch.ethz.matsim.supernetwork.supernet.Supernet;

/**
 * @author stefanopenazzi
 *
 */
public final class LinksTrafficFlowComputation implements IterationEndsListener {

	private Supernet supernet;
	
	@Inject
	public LinksTrafficFlowComputation (Supernet supernet) {
		this.supernet = supernet;
	}
	
	@Override
	public void notifyIterationEnds(IterationEndsEvent event) {
		supernet.treesCalculation();
	}
}
