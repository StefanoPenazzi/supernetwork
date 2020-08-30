/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router.cluster;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.vehicles.Vehicle;

import ch.ethz.matsim.dedalo.routing.manager.RoutingGeneralManager;

/**
 * @author stefanopenazzi
 *
 */
public class ClusterLeastCostPathCalculator implements LeastCostPathCalculator {

	private final RoutingGeneralManager conatinerManager;
	
	ClusterLeastCostPathCalculator(RoutingGeneralManager conatinerManager){
		this.conatinerManager = conatinerManager;
 	}
	
	@Override
	public Path calcLeastCostPath(Node fromNode, Node toNode, double starttime, Person person, Vehicle vehicle) {
		
		return conatinerManager.getPath(fromNode, toNode, starttime, "car");
	}

}
