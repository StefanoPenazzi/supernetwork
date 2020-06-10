/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import java.util.List;

import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.router.TripStructureUtils.Trip;
import org.matsim.core.scoring.functions.ScoringParametersForPerson;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Graph;
import ch.ethz.matsim.supernetwork.replanning.supernetwork.SupernetworkModel;

/**
 * @author stefanopenazzi
 *
 */
public class tdspOrdaRom implements SupernetworkModel {

	private final ContainerManager containerManager;
	private final ScoringParametersForPerson params;
	private final TripRouter tripRouter;
	
	
	
	@Inject
	public tdspOrdaRom(ContainerManager containerManager,ScoringParametersForPerson params,TripRouter tripRouter) {
		this.containerManager = containerManager;
		this.params = params;
		this.tripRouter = tripRouter;
	}
	
	@Override
	public List<? extends PlanElement> newPlan(Plan plan) {
		// create the graph from the plan
		
		//find the optimal solution
		return null;
	}
	
	private Graph createGraph(Plan plan) {
		final List<Trip> trips = TripStructureUtils.getTrips( plan , tripRouter.getStageActivityTypes() );
		
		return null;
	}

	
}
