/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.vehicles.Vehicle;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkLeastCostPathCalculator implements LeastCostPathCalculator {

	private final ContainerManager conatinerManager;
	
	SupernetworkLeastCostPathCalculator(ContainerManager conatinerManager){
		this.conatinerManager = conatinerManager;
 	}
	
	@Override
	public Path calcLeastCostPath(Node fromNode, Node toNode, double starttime, Person person, Vehicle vehicle) {
		
		return conatinerManager.getPath(fromNode, toNode, starttime, "car");
	}

}
