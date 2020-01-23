/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import java.util.Map;

import com.google.inject.Provides;

import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.models.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.subnetwork.SubnetworkFactory;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		// TODO Auto-generated method stub
		
	}
	@Provides
	public Subnetwork provideSubnetwork(Map<String,SubnetworkFactory> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getSubnetwork()) {
		case DEFAULT:
			return null ;
		default:
			throw new IllegalStateException("The param Subnetwork in the module Supernetwork is not allowed.");
		}
	}

}
