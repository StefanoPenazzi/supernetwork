/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import java.util.ArrayList;
import java.util.List;

import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;

import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.subnetwork.SubnetworkFactory;

/**
 * @author stefanopenazzi
 *
 */
public class RoutingGeneralManagerFactoryImpl implements RoutingGeneralManagerFactory, StartupListener {

	private final ClustersContainer clustersContainer;
	private final MiddlenetworkFactory middlenetworkFactory; 
	private final RoutingGeneralManager routingGeneralManager;

	
	
	@Inject
	public RoutingGeneralManagerFactoryImpl(
			ClustersContainer clustersContainer,
			MiddlenetworkFactory middlenetworkFactory,
			RoutingGeneralManager routingGeneralManager
			
    ) {
		this.clustersContainer = clustersContainer;
		this.middlenetworkFactory = middlenetworkFactory; 
		this.routingGeneralManager = routingGeneralManager;

	}
	
	private List<Middlenetwork> containerManagerByClusteringActivities() {
		List<Middlenetwork> middlenetworks = new ArrayList<Middlenetwork>();
		List<ClusterActivitiesLocation> lc = this.clustersContainer.getClusters();
		for(ClusterActivitiesLocation cdi: lc) {
			//Subnetwork sn = subnetworkFactory.generateSubnetworkByCluster(cdi); 
			middlenetworks.add(middlenetworkFactory.create(cdi, null));
		}
		return middlenetworks;
	}
	
	@Override
	public void setContainerManager() {
		
		routingGeneralManager.initialize(containerManagerByClusteringActivities());
	}
	
	@Override
	public void notifyStartup(StartupEvent event) {
	     this.setContainerManager();
	}

}
