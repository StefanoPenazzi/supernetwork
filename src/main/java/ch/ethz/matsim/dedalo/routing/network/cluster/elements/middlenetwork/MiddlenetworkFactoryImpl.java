/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster.Cluster;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.subnetwork.Subnetwork;

/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkFactoryImpl implements MiddlenetworkFactory {
	
	
	@Inject
	public MiddlenetworkFactoryImpl() {
		
	}

	@Override
	public Middlenetwork create(Cluster cluster,Subnetwork subnetwork) {
		return new MiddlenetworkImpl(cluster,subnetwork);
	}

}
