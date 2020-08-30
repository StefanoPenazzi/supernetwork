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
	
    private RoutingGeneralManager routingGeneralManager;
	
	@Inject
	public RoutingManagerUpdate (RoutingGeneralManager routingGeneralManager) {
		this.routingGeneralManager = routingGeneralManager;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		routingGeneralManager.updateContainer("car");
	}

}
