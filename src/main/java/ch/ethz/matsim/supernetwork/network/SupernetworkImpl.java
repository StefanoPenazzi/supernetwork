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
public class SupernetworkImpl implements Supernetwork{

	private final static Logger log = Logger.getLogger(SupernetworkImpl.class);
	
	private  ContainerManager containerManager;
	
	@Override
	public ContainerManager getContainerManager() {
		return this.containerManager;
	}

	@Override
	public void setContainerManager(ContainerManager containerManager) {
		this.containerManager = containerManager;
	}

	@Override
	public void containerUpdate() {
		this.containerManager.updateContainer("car");		
	}

	@Override
	public Path getPath(Activity act, Node toNode, int time, String mode) {
		return this.containerManager.getPath(act, toNode ,time, mode);
	}	
}
