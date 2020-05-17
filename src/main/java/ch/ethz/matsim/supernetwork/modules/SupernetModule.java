/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainerImpl;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetModule extends AbstractSupernetworkExtension{

	public static final String DEFAULT = "Default";
	
	@Override
	protected void installExtension() {
		bind(SupernetworkRoutesContainer.class).to(SupernetworkRoutesContainerImpl.class).asEagerSingleton();
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
