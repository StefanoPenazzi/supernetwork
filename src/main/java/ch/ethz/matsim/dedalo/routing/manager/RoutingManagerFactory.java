/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import java.util.List;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutingManagerFactory {

	public RoutingManager createRoutingManager(List<Middlenetwork> middlenetworks);
}
