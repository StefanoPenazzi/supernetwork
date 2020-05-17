/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.events;

import org.matsim.core.controler.events.IterationEndsEvent;
import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationEndsListener;
import org.matsim.core.controler.listener.IterationStartsListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.Supernet;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetContainerFilling implements IterationStartsListener{
	
    private Supernet supernet;
	
	@Inject
	public SupernetContainerFilling (Supernet supernet) {
		this.supernet = supernet;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		supernet.treesCalculation();
	}

}
