/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import java.util.Map;

import com.google.inject.Provides;
import ch.ethz.matsim.supernetwork.models.subnetwork_models.SubnetworkFromActivitiesCluster;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.MiddlenetworkFactoryFromRegion;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.MiddlenetworkFactoryImpl;


/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	public static final String FROMREGION = "FromRegion";
	
	@Override
	protected void installExtension() {
		bindMiddlenetworkFactory(DEFAULT).to(MiddlenetworkFactoryImpl.class);
		bindMiddlenetworkFactory(FROMREGION).to(MiddlenetworkFactoryFromRegion.class);
		
	}
	
	@Provides
	public MiddlenetworkFactory provideSubnetwork(Map<String,MiddlenetworkFactory> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getMiddlenetwork()) {
		case DEFAULT:
			return msc.get(DEFAULT) ;
		case FROMREGION:
			return msc.get(FROMREGION) ;
		default:
			throw new IllegalStateException("The param Middlenetwork in the module Supernetwork is not allowed.");
		}
	}

}
