/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import org.matsim.api.core.v01.network.Network;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.core.router.util.LeastCostPathCalculatorFactory;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkLeastCostPathCalculatorFactoryImpl implements LeastCostPathCalculatorFactory{

	private final ContainerManager conatinerManager;
	
	@Inject
	SupernetworkLeastCostPathCalculatorFactoryImpl(ContainerManager containerManager){
		this.conatinerManager = containerManager;
	}
	
	@Override
	public LeastCostPathCalculator createPathCalculator(Network network, TravelDisutility travelCosts,
			TravelTime travelTimes) {
		
		return new SupernetworkLeastCostPathCalculator(conatinerManager);
	}

}
