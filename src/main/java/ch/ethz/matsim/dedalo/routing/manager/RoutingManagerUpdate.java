/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;

import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;
import com.google.inject.Inject;


/**
 * @author stefanopenazzi
 *
 */
public class RoutingManagerUpdate implements IterationStartsListener{
	
    private ContainerManager containerManager;
	
	@Inject
	public RoutingManagerUpdate (ContainerManager containerManager) {
		this.containerManager = containerManager;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		containerManager.updateContainer("car");
	}

}
