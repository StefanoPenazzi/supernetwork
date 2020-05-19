/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.rerouting;

import java.util.List;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.config.Config;
import org.matsim.core.population.algorithms.PlanAlgorithm;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.router.TripStructureUtils.Trip;
import org.matsim.core.utils.misc.Time;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;

/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteModelAlgorithm implements PlanAlgorithm {
	
	private final ActivityFacilities facilities;
	private final ClustersReRouteModel clusterReRouteModel;
	private final TripRouter tripRouter;
	
	public ClustersReRouteModelAlgorithm(ClustersReRouteModel clusterReRouteModel, ActivityFacilities facilities,
			TripRouter tripRouter) {
		this.clusterReRouteModel = clusterReRouteModel;
		this.facilities = facilities;
		this.tripRouter = tripRouter;
	}

	@Override
	public void run(Plan plan) {
		final List<Trip> trips = TripStructureUtils.getTrips( plan , tripRouter.getStageActivityTypes() );

		for (Trip oldTrip : trips) {
			
			if(tripRouter.getMainModeIdentifier().identifyMainMode( oldTrip.getTripElements() ) == "car") {
			
				final List<? extends PlanElement> newTrip =
						clusterReRouteModel.calcRoute( oldTrip.getOriginActivity(),
								 FacilitiesUtils.toFacility( oldTrip.getDestinationActivity(), facilities ),
								calcEndOfActivity( oldTrip.getOriginActivity() , plan, tripRouter.getConfig() ),
								plan.getPerson() );
				//putVehicleFromOldTripIntoNewTripIfMeaningful(oldTrip, newTrip);
				if(newTrip != null) {
					TripRouter.insertTrip(
							plan, 
							oldTrip.getOriginActivity(),
							newTrip,
							oldTrip.getDestinationActivity());
					}
			}
		}
	}
	
	public static double calcEndOfActivity(
			final Activity activity,
			final Plan plan,
			final Config config ) {
		// yyyy similar method in PopulationUtils.  TripRouter.calcEndOfPlanElement in fact uses it.  However, this seems doubly inefficient; calling the
		// method in PopulationUtils directly would probably be faster.  kai, jul'19

		if (!Time.isUndefinedTime(activity.getEndTime())) return activity.getEndTime();

		// no sufficient information in the activity...
		// do it the long way.
		// XXX This is inefficient! Using a cache for each plan may be an option
		// (knowing that plan elements are iterated in proper sequence,
		// no need to re-examine the parts of the plan already known)
		double now = 0;

		for (PlanElement pe : plan.getPlanElements()) {
			now = TripRouter.calcEndOfPlanElement( now, pe, config );
			if (pe == activity) return now;
		}

		throw new RuntimeException( "activity "+activity+" not found in "+plan.getPlanElements() );
	}

}
