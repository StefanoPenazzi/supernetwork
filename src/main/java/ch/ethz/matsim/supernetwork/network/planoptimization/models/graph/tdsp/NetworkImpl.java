/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;



/**
 * @author stefanopenazzi
 *
 */
public class NetworkImpl implements Network{

	private final static Logger log = Logger.getLogger(NetworkImpl.class);
	
	private final  ContainerManager containerManager;
	
	public NetworkImpl(ContainerManager containerManager) {
		this.containerManager = containerManager;
	}
	
	public ContainerManager getContainerManager() {
		return this.containerManager;
	}
}
