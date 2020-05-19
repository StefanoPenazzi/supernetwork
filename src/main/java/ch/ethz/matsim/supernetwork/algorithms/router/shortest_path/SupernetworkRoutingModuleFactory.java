/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.List;

import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutingModuleFactory {
	
	public SupernetworkRoutingModule getSupernetworkRoutingModule(String mode,List<Middlenetwork> middlenetworks);

}
