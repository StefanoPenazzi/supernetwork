/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import java.util.Map;
import com.google.inject.Provides;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.supernetwork.routing.network.cluster.models.subnetwork_models.SubnetworkFromActivitiesCluster;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		bindSubnetworkFactory(DEFAULT).to(SubnetworkFromActivitiesCluster.Factory.class);
		
		
	}
	@Provides
	public SubnetworkFactory provideSubnetwork(Map<String,SubnetworkFactory> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getSubnetwork()) {
		case DEFAULT:
			return  msc.get(DEFAULT);
		default:
			throw new IllegalStateException("The param Subnetwork in the module Supernetwork is not allowed.");
		}
	}

}
