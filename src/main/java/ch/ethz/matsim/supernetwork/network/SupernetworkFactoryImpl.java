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
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManagerFactory;
import ch.ethz.matsim.supernetwork.network.utilities.SupernetPrint;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.networkelements.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.networkelements.subnetwork.SubnetworkFactory;




/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkFactoryImpl implements SupernetworkFactory {
	
	private final static Logger log = Logger.getLogger(SupernetworkFactoryImpl.class);
	
	Supernetwork supernet;
	ClustersContainer clustersContainer;
	Scenario scenario;
	SupernetworkConfigGroup supernetworkConfigGroup;
	MatsimServices matsimServices;
	SubnetworkFactory subnetworkFactory; 
	MiddlenetworkFactory middlenetworkFactory;
	SupernetPrint supernetPrint;
	ContainerManagerFactory containerManagerFactory; 

	
	@Inject
	public SupernetworkFactoryImpl(Supernetwork supernet, ClustersContainer clustersContainer, Scenario scenario,MatsimServices matsimServices,
			SubnetworkFactory subnetworkFactory,MiddlenetworkFactory middlenetworkFactory,
			SupernetPrint supernetPrint,ContainerManagerFactory containerManagerFactory) {//, SupernetworkConfigGroup supernetworkConfigGroup) {
		this.supernet = supernet;
		this.clustersContainer = clustersContainer;
		this.scenario = scenario;
		this.matsimServices = matsimServices;
		this.subnetworkFactory = subnetworkFactory;
		this.middlenetworkFactory = middlenetworkFactory;
		this.supernetPrint = supernetPrint;
		this.containerManagerFactory = containerManagerFactory;
		create();
		//this.supernetworkConfigGroup = supernetworkConfigGroup;
	}

	@Override
	public
	final void create() {
		
		List<Middlenetwork> middlenetworks = new ArrayList<Middlenetwork>();
		List<ClusterActivitiesLocation> lc = this.clustersContainer.getClusters();
		for(ClusterActivitiesLocation cdi: lc) {
			Subnetwork sn = subnetworkFactory.generateSubnetworkByCluster(cdi); 
			middlenetworks.add(middlenetworkFactory.create(cdi, sn));
		}
		supernet.setContainerManager(containerManagerFactory.createContainerManager(middlenetworks));
		//this.supernetPrint.print();
	}
}
