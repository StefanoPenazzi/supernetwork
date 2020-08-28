/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.router;

import java.util.List;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.api.internal.MatsimExtensionPoint;
import org.matsim.core.api.internal.MatsimFactory;

import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.routing.router.LeastCostTreeCalculator;


/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkLeastCostTreeCalculatorFactory extends MatsimFactory, MatsimExtensionPoint {
	
	public LeastCostTreeCalculator createTreeCalculator(String routingMode);

	public void setRoutingNetwork(Network network, List<Middlenetwork> middlenetworks);
}
