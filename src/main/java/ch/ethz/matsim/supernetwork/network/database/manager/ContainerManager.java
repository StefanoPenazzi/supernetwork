/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager;

import java.util.List;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.network.networkelements.supernode.Supernode;


/**
 * @author stefanopenazzi
 *
 */
public interface ContainerManager {

	public void updateContainer(String mode);
	public Path getPath(Node fromNode, Node toNode ,double time,String mode);
	public void setMiddlenetworks(List<Middlenetwork> middlenetworks);
	public List<Middlenetwork> getMiddlenetworks();
}
