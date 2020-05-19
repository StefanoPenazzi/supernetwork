/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;


/**
 * @author stefanopenazzi
 *
 */
public interface ContainerManager {

	public void updateContainer(String mode);
	public Path getPath(Supernode supernode, Node toNode ,double time,String mode);
}
