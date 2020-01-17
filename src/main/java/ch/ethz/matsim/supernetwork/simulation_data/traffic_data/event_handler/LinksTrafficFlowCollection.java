/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.VehicleEntersTrafficEvent;
import org.matsim.api.core.v01.events.VehicleLeavesTrafficEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.events.handler.VehicleEntersTrafficEventHandler;
import org.matsim.api.core.v01.events.handler.VehicleLeavesTrafficEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.mobsim.jdeqsim.Vehicle;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkDataTTV;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;

/**
 * @author stefanopenazzi
 *
 */
public final class LinksTrafficFlowCollection implements LinkLeaveEventHandler, LinkEnterEventHandler,VehicleLeavesTrafficEventHandler {

	//the class collect data from the links selected by the query in a single iteration 
	
    public static final String FILENAME_MODESTATS = "LinksTrafficFlowCollectorImpl";

    private TrafficDataContainer container;
    
	@Inject
	LinksTrafficFlowCollection (TrafficDataContainer container){
		this.container = container;
	}

	@Override
	public void handleEvent(LinkEnterEvent event) {
		container.getInputFlows().get(event.getLinkId()).add(new LinkDataTTV((int)event.getTime(),true,event.getVehicleId()));
	}

	@Override
	public void handleEvent(LinkLeaveEvent event) {
		container.getOutputFlows().get(event.getLinkId()).add(new LinkDataTTV ((int)event.getTime(),true,event.getVehicleId()));
	}

	@Override
	public void handleEvent(VehicleLeavesTrafficEvent event) {
		container.getOutputFlows().get(event.getLinkId()).add(new LinkDataTTV ((int)event.getTime(),false,event.getVehicleId()));
	}
}