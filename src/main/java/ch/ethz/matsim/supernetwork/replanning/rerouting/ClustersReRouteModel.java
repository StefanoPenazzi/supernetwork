/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.rerouting;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.facilities.Facility;

/**
 * @author stefanopenazzi
 *
 */
public interface ClustersReRouteModel {
	
	public List<? extends PlanElement> calcRoute(Activity fromActivity,Facility toFacility,double departureTime,Person person);

}
