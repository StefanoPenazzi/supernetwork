/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.network.cluster.models.supernetwork_model;

import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManagerFactory;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlansForPopulationManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitializationEvent implements StartupListener {

	private final RoutingGeneralManagerFactory routingGeneralManagerFactory;
	private final PlansForPopulationManager plansForPopulationManager;
	private final ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph;
	
	@Inject
	SupernetworkInitializationEvent (RoutingGeneralManagerFactory routingGeneralManagerFactory,PlansForPopulationManager plansForPopulationManager,
			ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph){
		this.routingGeneralManagerFactory = routingGeneralManagerFactory;
		this.plansForPopulationManager = plansForPopulationManager;
		this.scoringFunctionsForPopulationGraph = scoringFunctionsForPopulationGraph;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		routingGeneralManagerFactory.setContainerManager();
		this.scoringFunctionsForPopulationGraph.init();
		plansForPopulationManager.init();
	}
}

