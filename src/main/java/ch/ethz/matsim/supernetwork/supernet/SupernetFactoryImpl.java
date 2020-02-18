/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.MatsimServices;

import com.google.inject.Inject;
import com.google.inject.Injector;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.subnetwork.SubnetworkFactory;



/**
 * @author stefanopenazzi
 *
 */
public class SupernetFactoryImpl implements SupernetFactory {
	
	Supernet supernet;
	//clusterContainer is injected this means that is going to use the injected scenario and 
	//config files to be initialized. 
	//TODO what happens if the scenario is null etc
	ClustersContainer clustersContainer;
	TrafficDataContainer trafficDataContainer;
	Scenario scenario;
	SupernetworkConfigGroup supernetworkConfigGroup;
	MatsimServices matsimServices;
	SubnetworkFactory subnetworkFactory; 
	MiddlenetworkFactory middlenetworkFactory;
	
	@Inject
	public SupernetFactoryImpl(Supernet supernet, ClustersContainer clustersContainer, Scenario scenario,MatsimServices matsimServices,
			TrafficDataContainer trafficDataContainer,SubnetworkFactory subnetworkFactory,MiddlenetworkFactory middlenetworkFactory) {//, SupernetworkConfigGroup supernetworkConfigGroup) {
		this.supernet = supernet;
		this.clustersContainer = clustersContainer;
		this.scenario = scenario;
		this.matsimServices = matsimServices;
		this.trafficDataContainer = trafficDataContainer;
		this.subnetworkFactory = subnetworkFactory;
		this.middlenetworkFactory = middlenetworkFactory;
		create();
		//this.supernetworkConfigGroup = supernetworkConfigGroup;
	}

	@Override
	public
	final void create() {
		
		supernet.setActivitiesClustersContainer(this.clustersContainer);
		List<Middlenetwork> middlenetworks = new ArrayList<Middlenetwork>();
		List<ClusterActivitiesLocation> lc = supernet.getActivitiesClusterContainer().getClusters();
		for(ClusterActivitiesLocation cdi: lc) {
			Subnetwork sn = subnetworkFactory.generateSubnetworkByCluster(cdi); 
			middlenetworks.add(middlenetworkFactory.create(cdi, sn));
		}
		supernet.setMiddlenetworks(middlenetworks);
		System.out.println();
	}
}
