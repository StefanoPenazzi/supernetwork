/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.manager;


import java.util.List;

import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutingManagerFactory {

	public RoutingManager createRoutingManager(List<Middlenetwork> middlenetworks);
}
