/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.router;

import java.util.List;

import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutingModuleFactory {
	
	public SupernetworkRoutingModule getSupernetworkRoutingModule(String mode,List<Middlenetwork> middlenetworks);

}
