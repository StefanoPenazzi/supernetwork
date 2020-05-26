/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.Subnetwork;

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
