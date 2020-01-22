/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import com.google.inject.Singleton;

import ch.ethz.matsim.supernetwork.halfnetwork.HalfnetworkFactory;
import ch.ethz.matsim.supernetwork.halfnetwork.HalfnetworkFactoryImpl;
import ch.ethz.matsim.supernetwork.models.supernetwork_model.SupernetworkInitializationEvent;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkDataTTV;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainerDefaultImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelTime;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler.LinksTrafficFlowCollection;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler.LinksTrafficFlowComputation;
import ch.ethz.matsim.supernetwork.supernet.Supernet;
import ch.ethz.matsim.supernetwork.supernet.SupernetFactory;
import ch.ethz.matsim.supernetwork.supernet.SupernetFactoryImpl;
import ch.ethz.matsim.supernetwork.supernet.SupernetImpl;

/**
 * @author stefanopenazzi
 *
 */
public class InitializationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		
		bind(SupernetImpl.class).in(Singleton.class);
		bind(Supernet.class).to(SupernetImpl.class);
		
		this.addControlerListenerBinding().to(SupernetworkInitializationEvent.class);
		
		bind(LinksTrafficFlowCollection.class).in(Singleton.class);
    	bind(LinksTrafficFlowComputation.class).in(Singleton.class);
    	bind(TrafficDataContainer.class).to(TrafficDataContainerDefaultImpl.class).asEagerSingleton();
    	bind(LinkData.class).to(LinkDataTTV.class);
    	bind(TravelData.class).to(TravelTime.class);
        this.addControlerListenerBinding().to(LinksTrafficFlowComputation.class);
        this.addEventHandlerBinding().to(LinksTrafficFlowCollection.class);
        bind(HalfnetworkFactory.class).to(HalfnetworkFactoryImpl.class);
        
        bind(SupernetFactory.class).to(SupernetFactoryImpl.class);
		
	}

}
