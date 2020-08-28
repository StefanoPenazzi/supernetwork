/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.router;

import org.matsim.core.router.util.NodeDataFactory;
import org.matsim.core.router.util.RoutingNetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface FastRouterDelegateFactory {

	public FastRouterDelegate createFastRouterDelegate(Dijkstra dijkstra,
			NodeDataFactory nodeDataFactory, RoutingNetwork routingNetwork);
}
