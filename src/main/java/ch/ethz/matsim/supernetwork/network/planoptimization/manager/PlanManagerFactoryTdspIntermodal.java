/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.manager;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.router.StageActivityTypes;
import org.matsim.core.router.TripRouter;
import org.matsim.facilities.ActivityFacilities;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp.OrdaRomOptimizationAlgorithm;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp.TdspIntermodalOptimizationAlgorithm;
import ch.ethz.matsim.supernetwork.network.utilities.ActivityManager;

/**
 * @author stefanopenazzi
 *
 */
public class PlanManagerFactoryTdspIntermodal implements PlanManagerFactory {

	private final PlanModelFactory planModelFactory;
	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	private final ContainerManager containerManager;
	private final PopulationFactory populationFactory;
	private final Network network;
	private final TripRouter tripRouter;
	private final ActivityFacilities facilities;
	private final ActivityManager activityManager;
	
	
	@Inject
	public PlanManagerFactoryTdspIntermodal(PlanModelFactory planModelFactory, ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,
			ContainerManager containerManager,PopulationFactory populationFactory,Network network,TripRouter tripRouter,ActivityFacilities facilities,
			ActivityManager activityManager) {
		this.planModelFactory = planModelFactory;
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		this.containerManager = containerManager;
		this.populationFactory = populationFactory;
		this.network = network;
		this.tripRouter = tripRouter;
		this.facilities = facilities;
		this.activityManager = activityManager;
	}
	
	@Override
	public PlanManager createPlanManager(Person person) {
		TdspIntermodalGraph planModel = (TdspIntermodalGraph) this.planModelFactory.createPlanModel(person);
		return new PlanManagerTdspIntermodal(planModel, new TdspIntermodalOptimizationAlgorithm(this.scoringFunctionForPopulationGraph,this.containerManager,
				this.populationFactory,this.network,this.tripRouter, this.facilities,this.activityManager));
	}

}
