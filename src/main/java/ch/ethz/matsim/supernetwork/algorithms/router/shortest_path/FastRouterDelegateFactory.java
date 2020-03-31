/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

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
