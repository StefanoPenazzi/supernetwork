/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import com.google.inject.Singleton;

import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkDataTTV;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainerDefaultImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelTime;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler.LinksTrafficFlowCollection;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler.LinksTrafficFlowComputation;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateSimulationDataModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		bind(LinksTrafficFlowCollection.class).in(Singleton.class);
    	bind(LinksTrafficFlowComputation.class).in(Singleton.class);
    	
        this.addControlerListenerBinding().to(LinksTrafficFlowComputation.class);
        this.addEventHandlerBinding().to(LinksTrafficFlowCollection.class);
        
        bind(TrafficDataContainer.class).to(TrafficDataContainerDefaultImpl.class).asEagerSingleton();
    	bind(LinkData.class).to(LinkDataTTV.class);
    	bind(TravelData.class).to(TravelTime.class);
	}

}
