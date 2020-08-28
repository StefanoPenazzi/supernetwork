/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.manager;


import ch.ethz.matsim.supernetwork.routing.router.SupernetworkLeastCostTreeCalculatorFactory;
import ch.ethz.matsim.supernetwork.routing.router.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.routing.router.SupernetworkRoutingModuleImpl;

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
