/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;

import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;


/**
 * @author stefanopenazzi
 *
 */
public class RoutingManagerInitialization implements StartupListener {

	private final RoutingGeneralManagerFactory routingGeneralManagerFactory;
	
	@Inject
	RoutingManagerInitialization (RoutingGeneralManagerFactory routingGeneralManagerFactory){
		this.routingGeneralManagerFactory = routingGeneralManagerFactory;
		
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		routingGeneralManagerFactory.setContainerManager();
	}
}