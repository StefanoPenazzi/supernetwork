/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.List;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.internal.MatsimExtensionPoint;
import org.matsim.core.api.internal.MatsimFactory;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.LeastCostTreeCalculator;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;


/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkLeastCostTreeCalculatorFactory extends MatsimFactory, MatsimExtensionPoint {
	
	public LeastCostTreeCalculator createTreeCalculator(String routingMode);

	public void setRoutingNetwork(Network network, List<Middlenetwork> middlenetworks);
}
