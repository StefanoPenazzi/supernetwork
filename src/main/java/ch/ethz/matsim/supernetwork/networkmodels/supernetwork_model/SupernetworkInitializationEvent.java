/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkmodels.supernetwork_model;

import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManagerFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.manager.PlanModelForPopulationManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitializationEvent implements StartupListener {

	private final ContainerManagerFactory containerManagerFactory;
	private final PlanModelForPopulationManager planModelForPopulationManager;
	private final ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph;
	
	@Inject
	SupernetworkInitializationEvent (ContainerManagerFactory containerManagerFactory,PlanModelForPopulationManager planModelForPopulationManager,
			ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph){
		this.containerManagerFactory = containerManagerFactory;
		this.planModelForPopulationManager = planModelForPopulationManager;
		this.scoringFunctionsForPopulationGraph = scoringFunctionsForPopulationGraph;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		containerManagerFactory.setContainerManager();
		this.scoringFunctionsForPopulationGraph.init();
		planModelForPopulationManager.init();
		System.out.println("...");
	}
}

