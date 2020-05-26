/**
 * 
 */
package ch.ethz.matsim.supernetwork.network;

import org.matsim.api.core.v01.population.Plan;

/**
 * @author stefanopenazzi
 *
 */
public interface NetworkFactory {

	public Network createNetwork(Plan plan);
}
