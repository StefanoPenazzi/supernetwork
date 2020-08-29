/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router;

import java.util.List;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutingModuleFactory {
	
	public SupernetworkRoutingModule getSupernetworkRoutingModule(String mode,List<Middlenetwork> middlenetworks);

}
