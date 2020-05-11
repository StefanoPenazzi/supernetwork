/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet.events;

import org.matsim.core.controler.events.IterationEndsEvent;
import org.matsim.core.controler.listener.IterationEndsListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.supernet.Supernet;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetContainerFilling implements IterationEndsListener{
	
    private Supernet supernet;
	
	@Inject
	public SupernetContainerFilling (Supernet supernet) {
		this.supernet = supernet;
	}
	
	@Override
	public void notifyIterationEnds(IterationEndsEvent event) {
		supernet.treesCalculation();
	}

}
