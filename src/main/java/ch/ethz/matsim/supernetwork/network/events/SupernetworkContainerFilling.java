/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.events;

import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerFilling implements IterationStartsListener{
	
    private ContainerManager containerManager;
	
	@Inject
	public SupernetworkContainerFilling (ContainerManager containerManager) {
		this.containerManager = containerManager;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		containerManager.updateContainer("car");
	}

}
