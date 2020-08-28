/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.manager;


import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.subnetwork.SubnetworkFactory;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerFactoryImpl implements ContainerManagerFactory {

	private final ClustersContainer clustersContainer;
	private final SubnetworkFactory subnetworkFactory; 
	private final MiddlenetworkFactory middlenetworkFactory; 
	private final ContainerManager containerManager;

	
	
	@Inject
	public ContainerManagerFactoryImpl(
			ClustersContainer clustersContainer,
			SubnetworkFactory subnetworkFactory, 
			MiddlenetworkFactory middlenetworkFactory,
			ContainerManager containerManager
			
    ) {
		this.clustersContainer = clustersContainer;
		this.subnetworkFactory = subnetworkFactory; 
		this.middlenetworkFactory = middlenetworkFactory; 
		this.containerManager = containerManager;

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
		
		containerManager.setMiddlenetworks(containerManagerByClusteringActivities());
	}

}
