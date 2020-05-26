/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.MatsimServices;
import org.matsim.core.router.util.TravelTime;
import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.network.Network;
import ch.ethz.matsim.supernetwork.network.database.containers.RoutesContainer;
import ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.network.utilities.SupernetPrint;
import ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.SubnetworkFactory;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerFactoryImpl implements ContainerManagerFactory {

	private final Map<String, UpdateAlgorithm> updateAlgorithmsMap;
	private final Map<String, RoutesContainer> containersMap;
	private final RoutingManagerFactory routingManagerFactory; 
	private final Map<String, TravelTime> travelTimes;
	private final ClustersContainer clustersContainer;
	private final SubnetworkFactory subnetworkFactory; 
	private final MiddlenetworkFactory middlenetworkFactory; 
	private final ContainerManager containerManager;

	
	
	@Inject
	public ContainerManagerFactoryImpl(RoutingManagerFactory routingManagerFactory,
			Map<String, UpdateAlgorithm> updateAlgorithmsMap,Map<String,
			RoutesContainer> containersMap,Map<String, TravelTime> travelTimes,
			ClustersContainer clustersContainer,
			SubnetworkFactory subnetworkFactory, 
			MiddlenetworkFactory middlenetworkFactory,
			ContainerManager containerManager
			
    ) {
		this.routingManagerFactory = routingManagerFactory;
		this.updateAlgorithmsMap = updateAlgorithmsMap;
		this.containersMap = containersMap;
		this.travelTimes = travelTimes;
		this.clustersContainer = clustersContainer;
		this.subnetworkFactory = subnetworkFactory; 
		this.middlenetworkFactory = middlenetworkFactory; 
		this.containerManager = containerManager;

	}
	
	private List<Middlenetwork> containerManagerByClusteringActivities() {
		List<Middlenetwork> middlenetworks = new ArrayList<Middlenetwork>();
		List<ClusterActivitiesLocation> lc = this.clustersContainer.getClusters();
		for(ClusterActivitiesLocation cdi: lc) {
			Subnetwork sn = subnetworkFactory.generateSubnetworkByCluster(cdi); 
			middlenetworks.add(middlenetworkFactory.create(cdi, sn));
		}
		return middlenetworks;
	}
	
	@Override
	public void setContainerManager() {
		
		containerManager.setMiddlenetworks(containerManagerByClusteringActivities());
	}

}
