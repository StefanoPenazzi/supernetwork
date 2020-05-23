/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.events;

import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.Network;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerFilling implements IterationStartsListener{
	
    private Network supernet;
	
	@Inject
	public SupernetworkContainerFilling (Network supernet) {
		this.supernet = supernet;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		supernet.containerUpdate();
	}

}
