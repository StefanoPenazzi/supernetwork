/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.events;

import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanModelForPopulationManager;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerFilling implements IterationStartsListener{
	
    private ContainerManager containerManager;
    private final PlanModelForPopulationManager planModelForPopulationManager;
	
	@Inject
	public SupernetworkContainerFilling (ContainerManager containerManager,PlanModelForPopulationManager planModelForPopulationManager) {
		this.containerManager = containerManager;
		this.planModelForPopulationManager = planModelForPopulationManager;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		containerManager.updateContainer("car");
		this.planModelForPopulationManager.populationNewPlans();
	}

}
