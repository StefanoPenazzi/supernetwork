/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;
import java.util.Map;
import com.google.inject.Provides;

import ch.ethz.matsim.supernetwork.models.clustering_models.RegionHierarchicalCS;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkDataTTV;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainerDefaultImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelTime;

/**
 * @author stefanopenazzi
 *
 */
public class SimulationDataModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		bindTrafficDataContainer(DEFAULT).to(TrafficDataContainerDefaultImpl.class);
    	bind(LinkData.class).to(LinkDataTTV.class);
    	bind(TravelData.class).to(TravelTime.class);
	}
	
	@Provides
	public TrafficDataContainer provideSubnetwork(Map<String,TrafficDataContainer> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getSimulationDataCollection()) {
		case DEFAULT:
			return msc.get(DEFAULT) ;
		default:
			throw new IllegalStateException("The param SimulationDataCollection in the module Supernetwork is not allowed.");
		}
	}

	

}
