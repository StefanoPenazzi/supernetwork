/**
 * 
 */
package ch.ethz.matsim.supernetwork.network;

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
	
	private  ContainerManager containerManager;
	
	@Override
	public ContainerManager getContainerManager() {
		return this.containerManager;
	}
}
