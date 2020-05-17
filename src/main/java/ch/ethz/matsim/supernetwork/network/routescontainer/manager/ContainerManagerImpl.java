/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Provider;

import org.matsim.core.router.RoutingModule;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainerImpl.Domain;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerImpl implements ContainerManager {
	
	private Map<String, Provider<UpdateAlgorithm>> updateAlgorithmProviders = new LinkedHashMap<>() ;
	
	@Inject
	public ContainerManagerImpl(Map<String, Provider<UpdateAlgorithm>> updateAlgorithmProviders) {
		this.updateAlgorithmProviders = updateAlgorithmProviders;
	}

	@Override
	public TreeMap<Domain, Path> updateContainer(TreeMap<Domain, Path> oldContainer) {
		// TODO Auto-generated method stub
		return null;
	}

}
