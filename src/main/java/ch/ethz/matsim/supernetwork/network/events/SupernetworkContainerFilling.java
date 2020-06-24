/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.events;

import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlansForPopulationManager;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkContainerFilling implements IterationStartsListener{
	
    private ContainerManager containerManager;
    private final PlansForPopulationManager plansForPopulationManager;
	
	@Inject
	public SupernetworkContainerFilling (ContainerManager containerManager,PlansForPopulationManager plansForPopulationManager) {
		this.containerManager = containerManager;
		this.plansForPopulationManager = plansForPopulationManager;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		containerManager.updateContainer("car");
		this.plansForPopulationManager.populationNewPlans();
	}

}
