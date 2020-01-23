/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import java.util.Map;

import com.google.inject.Provides;

import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;


/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		// TODO Auto-generated method stub
		
	}
	
	@Provides
	public Middlenetwork provideSubnetwork(Map<String,MiddlenetworkFactory> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getMiddlenetwork()) {
		case DEFAULT:
			return null ;
		default:
			throw new IllegalStateException("The param Middlenetwork in the module Supernetwork is not allowed.");
		}
	}

}
