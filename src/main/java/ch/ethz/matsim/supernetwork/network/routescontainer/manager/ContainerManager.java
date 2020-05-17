/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.TreeMap;

import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainerImpl.Domain;


/**
 * @author stefanopenazzi
 *
 */
public interface ContainerManager {

	public TreeMap<Domain,Path> updateContainer(TreeMap<Domain,Path> oldContainer);
}
