/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.events;

import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import com.google.inject.Inject;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlansForPopulationManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkInitialization implements StartupListener {

	private final PlansForPopulationManager plansForPopulationManager;
	private final ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph;
	
	@Inject
	SupernetworkInitialization (PlansForPopulationManager plansForPopulationManager,
			ScoringFunctionsForPopulationGraph scoringFunctionsForPopulationGraph){
		this.plansForPopulationManager = plansForPopulationManager;
		this.scoringFunctionsForPopulationGraph = scoringFunctionsForPopulationGraph;
	}

	@Override
	public void notifyStartup(StartupEvent event) {
		this.scoringFunctionsForPopulationGraph.init();
		plansForPopulationManager.init();
	}
}
