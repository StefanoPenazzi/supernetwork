/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router.cluster;

import java.util.List;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface ClusterRoutingModuleFactory {
	
	public ClusterRoutingModule getSupernetworkRoutingModule(String mode,List<Middlenetwork> middlenetworks);

}
