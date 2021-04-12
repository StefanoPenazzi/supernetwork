/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import java.util.List;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutingGeneralManager {

	public void updateContainer(String mode);
	public Path getPath(Node fromNode, Node toNode ,double time,String mode);
	public void initialize(List<Middlenetwork> middlenetworks);
	public List<Middlenetwork> getMiddlenetworks();
}
