/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterLeastCostTreeCalculatorFactory;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterRoutingModule;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterRoutingModuleImpl;

/**
 * @author stefanopenazzi
 *
 */
public class RoutingManagerImpl extends  AbstractMultithreadingRoutingManager {

	private final ClusterLeastCostTreeCalculatorFactory clusterLeastCostTreeCalculatorFactory;
	/**
	 * @param numOfThreads
	 */
	public RoutingManagerImpl(int numOfThreads,ClusterLeastCostTreeCalculatorFactory clusterLeastCostTreeCalculatorFactory) {
		super(numOfThreads);
		this.clusterLeastCostTreeCalculatorFactory = clusterLeastCostTreeCalculatorFactory;
	}
	
	@Override
	public ClusterRoutingModule  getSupernetworkRoutingModule() {
		return new ClusterRoutingModuleImpl("car",this.clusterLeastCostTreeCalculatorFactory);
	}

}
