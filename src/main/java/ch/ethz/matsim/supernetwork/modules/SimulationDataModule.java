/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;
import java.util.Map;
import com.google.inject.Provides;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;

/**
 * @author stefanopenazzi
 *
 */
public class SimulationDataModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		// TODO Auto-generated method stub
		
	}
	
	@Provides
	public TrafficDataContainer provideSubnetwork(Map<String,TrafficDataContainer> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getSimulationDataCollection()) {
		case DEFAULT:
			return null ;
		default:
			throw new IllegalStateException("The param SimulationDataCollection in the module Supernetwork is not allowed.");
		}
	}

	

}
