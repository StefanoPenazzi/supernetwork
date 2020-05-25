/**
 * 
 */
package ch.ethz.matsim.supernetwork.network;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;


/**
 * @author stefanopenazzi
 *
 */
public interface Network {
	public ContainerManager getContainerManager();
}
