/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router.cluster;

import java.util.List;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.api.internal.MatsimExtensionPoint;
import org.matsim.core.api.internal.MatsimFactory;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;


/**
 * @author stefanopenazzi
 *
 */
public interface ClusterLeastCostTreeCalculatorFactory extends MatsimFactory, MatsimExtensionPoint {
	
	public LeastCostTreeCalculator createTreeCalculator(String routingMode);

	public void setRoutingNetwork(Network network, List<Middlenetwork> middlenetworks);
}
