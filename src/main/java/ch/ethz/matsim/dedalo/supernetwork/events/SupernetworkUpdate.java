/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.events;

import org.matsim.core.controler.events.IterationStartsEvent;
import org.matsim.core.controler.listener.IterationStartsListener;
import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlansForPopulationManager;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkUpdate implements IterationStartsListener{
	
    //private RoutingGeneralManager routingGeneralManager;
    private final PlansForPopulationManager plansForPopulationManager;
	
	@Inject
	public SupernetworkUpdate (PlansForPopulationManager plansForPopulationManager) {
		//this.routingGeneralManager = routingGeneralManager;
		this.plansForPopulationManager = plansForPopulationManager;
	}
	
	@Override
	public void notifyIterationStarts(IterationStartsEvent event) {
		//if ClusterRouting is used, it must updated before the supernetwork
		//routingGeneralManager.updateContainer("car");
		this.plansForPopulationManager.populationNewPlans();
	}

}
