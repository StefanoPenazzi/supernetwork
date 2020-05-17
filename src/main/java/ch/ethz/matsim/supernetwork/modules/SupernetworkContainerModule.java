/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;

import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainerImpl;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.ContainerManagerImpl;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmImpl;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerModule extends AbstractModule {

	@Override
	public void install() {
		bind(SupernetworkRoutesContainer.class).to(SupernetworkRoutesContainerImpl.class);
		bind(UpdateAlgorithm.class).to(UpdateAlgorithmImpl.class);
		bind(ContainerManager.class).to(ContainerManagerImpl.class);
	}

	

}
