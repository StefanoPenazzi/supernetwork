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

	public void setContainerManager(ContainerManager containerManager);
	public ContainerManager getContainerManager();
	public void containerUpdate();
	public Path getPath(Activity act,Node toNode,int time,String mode);


}
