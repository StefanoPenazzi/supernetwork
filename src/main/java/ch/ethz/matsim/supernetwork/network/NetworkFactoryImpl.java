/**
 * 
 */
package ch.ethz.matsim.supernetwork.network;

import org.matsim.api.core.v01.population.Plan;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;

/**
 * @author stefanopenazzi
 *
 */
public class NetworkFactoryImpl implements NetworkFactory {

	private final ContainerManager containerManager;
	
	@Inject
	public NetworkFactoryImpl(ContainerManager containerManager) {
		this.containerManager = containerManager;
	}
	
	@Override
	public Network createNetwork(Plan plan) {
		
		return new NetworkImpl();
	}
	
	

}
