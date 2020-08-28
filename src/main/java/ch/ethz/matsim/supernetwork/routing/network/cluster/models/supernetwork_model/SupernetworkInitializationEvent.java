/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.models.supernetwork_model;

import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.routing.manager.ContainerManagerFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlansForPopulationManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitializationEvent implements StartupListener {

	private final ContainerManagerFactory containerManagerFactory;
	private final PlansForPopulationManager plansForPopulationManager;
	private final ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph;
	
	@Inject
	SupernetworkInitializationEvent (ContainerManagerFactory containerManagerFactory,PlansForPopulationManager plansForPopulationManager,
			ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph){
		this.containerManagerFactory = containerManagerFactory;
		this.plansForPopulationManager = plansForPopulationManager;
		this.scoringFunctionsForPopulationGraph = scoringFunctionsForPopulationGraph;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		containerManagerFactory.setContainerManager();
		this.scoringFunctionsForPopulationGraph.init();
		plansForPopulationManager.init();
	}
}

