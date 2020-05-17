/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.events;

import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.Supernetwork;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerFilling implements IterationStartsListener{
	
    private Supernetwork supernet;
	
	@Inject
	public SupernetworkContainerFilling (Supernetwork supernet) {
		this.supernet = supernet;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		supernet.treesCalculation();
	}

}
