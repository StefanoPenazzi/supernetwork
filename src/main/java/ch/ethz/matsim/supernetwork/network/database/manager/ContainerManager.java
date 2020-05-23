/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;


/**
 * @author stefanopenazzi
 *
 */
public interface ContainerManager {

	public void updateContainer(String mode);
	public Path getPath(Supernode supernode, Node toNode ,double time,String mode);
	public Path getPath(Activity activity, Node toNode ,double time,String mode);
	
	public List<Middlenetwork> getMiddlenetworks();
}
