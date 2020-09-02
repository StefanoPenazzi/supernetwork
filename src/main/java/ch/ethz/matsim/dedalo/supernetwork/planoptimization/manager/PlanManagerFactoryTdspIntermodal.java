/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.router.TripRouter;
import org.matsim.facilities.ActivityFacilities;
import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManager;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.PlanModelFactory;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.graph.tdsp.TdspIntermodalOptimizationAlgorithm;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.graph.tdsp.TdspIntermodalOptimizationAlgorithmDefaultRouter;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManager;

/**
 * @author stefanopenazzi
 *
 */
public class PlanManagerFactoryTdspIntermodal implements PlanManagerFactory {

	private final PlanModelFactory planModelFactory;
	private final ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph;
	//private final RoutingGeneralManager routingGeneralManager;
	private final PopulationFactory populationFactory;
	private final Network network;
	private final TripRouter tripRouter;
	private final ActivityFacilities facilities;
	private final ActivityManager activityManager;
	
	
	@Inject
	public PlanManagerFactoryTdspIntermodal(PlanModelFactory planModelFactory, ScoringFunctionsForPopulationGraph scoringFunctionForPopulationGraph,
			PopulationFactory populationFactory,Network network,TripRouter tripRouter,ActivityFacilities facilities,
			ActivityManager activityManager) { //RoutingGeneralManager routingGeneralManager,
		this.planModelFactory = planModelFactory;
		this.scoringFunctionForPopulationGraph = scoringFunctionForPopulationGraph;
		//this.routingGeneralManager = routingGeneralManager;
		this.populationFactory = populationFactory;
		this.network = network;
		this.tripRouter = tripRouter;
		this.facilities = facilities;
		this.activityManager = activityManager;
	}
	
	@Override
	public PlanManager createPlanManager(Person person) {
		TdspIntermodalGraph planModel = (TdspIntermodalGraph) this.planModelFactory.createPlanModel(person);
		return new PlanManagerTdspIntermodal(planModel, new TdspIntermodalOptimizationAlgorithmDefaultRouter(this.scoringFunctionForPopulationGraph,
				this.populationFactory,this.network,this.tripRouter, this.facilities,this.activityManager));
	}

}
