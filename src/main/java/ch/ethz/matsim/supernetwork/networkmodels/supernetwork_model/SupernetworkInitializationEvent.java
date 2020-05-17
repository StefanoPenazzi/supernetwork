/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkmodels.supernetwork_model;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.SupernetworkFactory;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitializationEvent implements StartupListener {

	Scenario scenario;
	SupernetworkFactory supernetFactory;
	
	@Inject
	SupernetworkInitializationEvent (Scenario scenario,SupernetworkFactory supernetFactory){
		this.scenario = scenario;
		this.supernetFactory = supernetFactory;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		System.out.println("...");
	}

}
