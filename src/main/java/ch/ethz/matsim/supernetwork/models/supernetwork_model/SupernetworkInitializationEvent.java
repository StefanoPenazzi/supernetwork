/**
 * 
 */
package ch.ethz.matsim.supernetwork.models.supernetwork_model;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.events.BeforeMobsimEvent;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.BeforeMobsimListener;
import org.matsim.core.controler.listener.StartupListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.supernet.SupernetFactory;
import ch.ethz.matsim.supernetwork.supernet.SupernetFactoryImpl;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitializationEvent implements StartupListener {

	Scenario scenario;
	SupernetFactory supernetFactory;
	
	@Inject
	SupernetworkInitializationEvent (Scenario scenario,SupernetFactory supernetFactory){
		this.scenario = scenario;
		this.supernetFactory = supernetFactory;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		this.supernetFactory.create();
	}

}

