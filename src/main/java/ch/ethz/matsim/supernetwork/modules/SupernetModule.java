/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import java.util.Map;

import com.google.inject.Provides;

import ch.ethz.matsim.supernetwork.models.subnetwork_models.SubnetworkFromActivitiesCluster;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.supernet.Supernet;
import ch.ethz.matsim.supernetwork.supernet.SupernetFactory;
import ch.ethz.matsim.supernetwork.supernet.SupernetRoutesContainer;
import ch.ethz.matsim.supernetwork.supernet.SupernetRoutesContainerImpl;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		bind(SupernetRoutesContainer.class).to(SupernetRoutesContainerImpl.class).asEagerSingleton();
		install(new SupernetworkTripRouterModule());
		
	}

//	@Provides
//	public Supernet provideSupernet(Map<String,SupernetFactory> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
//		switch (supernetworkConfigGroup.getMiddlenetwork()) {
//		case DEFAULT:
//			return null ;
//		default:
//			throw new IllegalStateException("The param Supernet in the module Supernetwork is not allowed.");
//		}
//	}

}
