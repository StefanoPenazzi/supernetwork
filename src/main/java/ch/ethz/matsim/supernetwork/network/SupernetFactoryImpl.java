/**
 * 
 */
package ch.ethz.matsim.supernetwork.network;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.MatsimServices;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModuleFactory;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.network.utilities.SupernetPrint;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.networkelements.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.networkelements.subnetwork.SubnetworkFactory;




/**
 * @author stefanopenazzi
 *
 */
public class SupernetFactoryImpl implements SupernetFactory {
	
	private final static Logger log = Logger.getLogger(SupernetFactoryImpl.class);
	
	Supernet supernet;
	//clusterContainer is injected this means that is going to use the injected scenario and 
	//config files to be initialized. 
	//TODO what happens if the scenario is null etc
	ClustersContainer clustersContainer;
	Scenario scenario;
	SupernetworkConfigGroup supernetworkConfigGroup;
	MatsimServices matsimServices;
	SubnetworkFactory subnetworkFactory; 
	MiddlenetworkFactory middlenetworkFactory;
	SupernetworkRoutingModuleFactory supernetworkRoutingModuleFactory;
	SupernetPrint supernetPrint;

	
	@Inject
	public SupernetFactoryImpl(Supernet supernet, ClustersContainer clustersContainer, Scenario scenario,MatsimServices matsimServices,
			SubnetworkFactory subnetworkFactory,MiddlenetworkFactory middlenetworkFactory,
			SupernetworkRoutingModuleFactory supernetworkRoutingModuleFactory,SupernetPrint supernetPrint) {//, SupernetworkConfigGroup supernetworkConfigGroup) {
		this.supernet = supernet;
		this.clustersContainer = clustersContainer;
		this.scenario = scenario;
		this.matsimServices = matsimServices;
		this.subnetworkFactory = subnetworkFactory;
		this.middlenetworkFactory = middlenetworkFactory;
		this.supernetworkRoutingModuleFactory = supernetworkRoutingModuleFactory;
		this.supernetPrint = supernetPrint;
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
		supernet.setSupernetworkRoutingModule(supernetworkRoutingModuleFactory.getSupernetworkRoutingModule("car"));
		
		this.supernetPrint.print();
		
		System.out.println();
	}
}
