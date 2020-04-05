/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkRoutingModuleFactory {
	
	public SupernetworkRoutingModule getSupernetworkRoutingModule(String mode);

}
