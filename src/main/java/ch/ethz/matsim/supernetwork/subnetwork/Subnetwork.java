/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import org.matsim.api.core.v01.network.Network;

/**
 * @author stefanopenazzi
 *
 */
public interface Subnetwork {

	public Network getNetwork();
	public void setNetwork(Network network);
}
