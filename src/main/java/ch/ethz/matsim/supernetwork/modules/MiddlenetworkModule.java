/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import java.util.Map;

import com.google.inject.Provides;

import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.middlenetwork.MiddlenetworkFactoryImpl;
import ch.ethz.matsim.supernetwork.models.subnetwork_models.SubnetworkFromActivitiesCluster;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;


/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		bindMiddlenetworkFactory(DEFAULT).to(MiddlenetworkFactoryImpl.class);
		
	}
	
	@Provides
	public MiddlenetworkFactory provideSubnetwork(Map<String,MiddlenetworkFactory> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getMiddlenetwork()) {
		case DEFAULT:
			return msc.get(DEFAULT) ;
		default:
			throw new IllegalStateException("The param Middlenetwork in the module Supernetwork is not allowed.");
		}
	}

}
