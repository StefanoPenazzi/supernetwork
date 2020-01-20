/**
 * 
 */
package ch.ethz.matsim.supernetwork.halfnetwork;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public class HalfnetworkFactoryImpl implements HalfnetworkFactory {
	
	private final Provider<TrafficDataContainer> trafficDataContainerProvider;
	
	@Inject
	public HalfnetworkFactoryImpl(Provider<TrafficDataContainer> trafficDataContainerProvider) {
		this.trafficDataContainerProvider = trafficDataContainerProvider;
	}

	@Override
	public Halfnetwork create(Cluster cluster,Subnetwork subnetwork) {
		return new HalfnetworkImpl(this.trafficDataContainerProvider.get(),cluster,subnetwork);
	}

}
