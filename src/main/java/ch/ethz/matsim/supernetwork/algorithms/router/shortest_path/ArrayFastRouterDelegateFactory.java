/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import org.matsim.core.router.util.ArrayRoutingNetwork;
import org.matsim.core.router.util.NodeDataFactory;
import org.matsim.core.router.util.RoutingNetwork;

/**
 * @author stefanopenazzi
 *
 */
public class ArrayFastRouterDelegateFactory implements FastRouterDelegateFactory {

	@Override
	public ArrayFastRouterDelegate createFastRouterDelegate(Dijkstra dijkstra,
			NodeDataFactory nodeDataFactory, RoutingNetwork routingNetwork) {
		return new ArrayFastRouterDelegate(dijkstra, nodeDataFactory, (ArrayRoutingNetwork) routingNetwork);
	}
}
