/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.config.ReflectiveConfigGroup;
import org.matsim.core.controler.AbstractModule;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ch.ethz.matsim.supernetwork.halfnetwork.HalfnetworkFactory;
import ch.ethz.matsim.supernetwork.halfnetwork.HalfnetworkFactoryImpl;
import ch.ethz.matsim.supernetwork.model.ClusteringModel;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
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
public class SupernetworkModule extends AbstractModule {

	public static final String STRATEGY_NAME = "Supernetwork";
	
	//@Inject
	//private SupernetworkConfigGroup supernetworkConfig;
	
	@Override
	public void install() {
		// remove all the other replanning strategies
		
		//insert only supernetwork
		//install(new modulesSet());
		bind(LinksTrafficFlowCollection.class).in(Singleton.class);
    	bind(LinksTrafficFlowComputation.class).in(Singleton.class);
    	bind(ClusteringModel.class).in(Singleton.class);
    	bind(TrafficDataContainer.class).to(TrafficDataContainerDefaultImpl.class).asEagerSingleton();
    	bind(LinkData.class).to(LinkDataTTV.class);
    	bind(TravelData.class).to(TravelTime.class);
        this.addControlerListenerBinding().to(LinksTrafficFlowComputation.class);
        this.addEventHandlerBinding().to(LinksTrafficFlowCollection.class);
        bind(HalfnetworkFactory.class).to(HalfnetworkFactoryImpl.class);
        this.addControlerListenerBinding().to(ClusteringModel.class);
		
	}

}
