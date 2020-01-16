/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulationData.trafficData.eventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.mobsim.jdeqsim.Vehicle;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.simulationData.trafficData.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.simulationData.trafficData.container.TrafficDataContainerDefaultImpl;

/**
 * @author stefanopenazzi
 *
 */
public final class LinksTrafficFlowCollectorImpl implements LinkLeaveEventHandler, LinkEnterEventHandler {

	//the class collect data from the links selected by the query in a single iteration 
	
    public static final String FILENAME_MODESTATS = "LinksTrafficFlowCollectorImpl";

    private TrafficDataContainer container;
    
	@Inject
	LinksTrafficFlowCollectorImpl (TrafficDataContainer container){
		this.container = container;
	}

	@Override
	public void handleEvent(LinkEnterEvent event) {
		container.getInputFlows().get(event.getLinkId()).add((int)event.getTime());
	}

	@Override
	public void handleEvent(LinkLeaveEvent event) {
		container.getOutputFlows().get(event.getLinkId()).add((int)event.getTime());
	}
}
