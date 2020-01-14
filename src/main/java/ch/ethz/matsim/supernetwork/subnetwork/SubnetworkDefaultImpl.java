/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import org.matsim.api.core.v01.network.Network;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkDefaultImpl implements Subnetwork {

	Network network = null;
	
	@Override
	public Network getNetwork() {
		return this.network;
	}

	@Override
	public void setNetwork(Network network) {
		this.network = network;
		
	}

}
