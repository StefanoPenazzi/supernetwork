/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkLeastCostTreeCalculatorFactory;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleImpl;

/**
 * @author stefanopenazzi
 *
 */
public class RoutingManagerImpl extends  AbstractMultithreadingRoutingManager {

	private final SupernetworkLeastCostTreeCalculatorFactory supernetworkLeastCostTreeCalculatorFactory;
	/**
	 * @param numOfThreads
	 */
	public RoutingManagerImpl(int numOfThreads,SupernetworkLeastCostTreeCalculatorFactory supernetworkLeastCostTreeCalculatorFactory) {
		super(numOfThreads);
		this.supernetworkLeastCostTreeCalculatorFactory = supernetworkLeastCostTreeCalculatorFactory;
	}
	
	@Override
	public SupernetworkRoutingModule  getSupernetworkRoutingModule() {
		return new SupernetworkRoutingModuleImpl("car",this.supernetworkLeastCostTreeCalculatorFactory);
	}

}
