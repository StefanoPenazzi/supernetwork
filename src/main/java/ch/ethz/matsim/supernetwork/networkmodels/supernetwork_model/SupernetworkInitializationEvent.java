/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkmodels.supernetwork_model;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.NetworkFactory;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManagerFactory;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitializationEvent implements StartupListener {

	ContainerManagerFactory containerManagerFactory;
	
	@Inject
	SupernetworkInitializationEvent (ContainerManagerFactory containerManagerFactory){
		this.containerManagerFactory = containerManagerFactory;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		containerManagerFactory.setContainerManager();
		System.out.println("...");
	}

}

