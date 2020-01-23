/**
 * 
 */
package ch.ethz.matsim.supernetwork.middlenetwork;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkFactoryImpl implements MiddlenetworkFactory {
	
	private final TrafficDataContainer trafficDataContainer;
	
	@Inject
	public MiddlenetworkFactoryImpl(TrafficDataContainer trafficDataContainer) {
		this.trafficDataContainer = trafficDataContainer;
	}

	@Override
	public Middlenetwork create(Cluster cluster,Subnetwork subnetwork) {
		return new MiddlenetworkImpl(this.trafficDataContainer,cluster,subnetwork);
	}

}
