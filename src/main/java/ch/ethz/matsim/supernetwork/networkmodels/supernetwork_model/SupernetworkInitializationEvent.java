/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkmodels.supernetwork_model;

import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManagerFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanModelForPopulationManager;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitializationEvent implements StartupListener {

	private final ContainerManagerFactory containerManagerFactory;
	private final PlanModelForPopulationManager planModelForPopulationManager;
	
	@Inject
	SupernetworkInitializationEvent (ContainerManagerFactory containerManagerFactory,PlanModelForPopulationManager planModelForPopulationManager){
		this.containerManagerFactory = containerManagerFactory;
		this.planModelForPopulationManager = planModelForPopulationManager;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		containerManagerFactory.setContainerManager();
		planModelForPopulationManager.init();
		System.out.println("...");
	}

}

