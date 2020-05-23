/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.List;

import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutingManagerFactory {

	public RoutingManager createRoutingManager(List<Middlenetwork> middlenetworks);
}
