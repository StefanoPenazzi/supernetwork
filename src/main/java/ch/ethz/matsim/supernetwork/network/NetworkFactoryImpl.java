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
import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
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
public class NetworkFactoryImpl implements NetworkFactory {
	
	private final static Logger log = Logger.getLogger(NetworkFactoryImpl.class);
	
	Network supernet;
	ClustersContainer clustersContainer;
	Scenario scenario;
	SupernetworkConfigGroup supernetworkConfigGroup;
	MatsimServices matsimServices;
	SubnetworkFactory subnetworkFactory; 
	MiddlenetworkFactory middlenetworkFactory;
	SupernetPrint supernetPrint;
	ContainerManager containerManager; 

	
	@Inject
	public NetworkFactoryImpl(Network supernet, ClustersContainer clustersContainer, Scenario scenario,MatsimServices matsimServices,
			SubnetworkFactory subnetworkFactory,MiddlenetworkFactory middlenetworkFactory,
			SupernetPrint supernetPrint,ContainerManager containerManager) {//, SupernetworkConfigGroup supernetworkConfigGroup) {
		this.supernet = supernet;
		this.clustersContainer = clustersContainer;
		this.scenario = scenario;
		this.matsimServices = matsimServices;
		this.subnetworkFactory = subnetworkFactory;
		this.middlenetworkFactory = middlenetworkFactory;
		this.supernetPrint = supernetPrint;
		this.containerManager = containerManager;
		create();
		//this.supernetworkConfigGroup = supernetworkConfigGroup;
	}

	@Override
	public
	final void create() {
		
		supernet.setContainerManager(containerManager);
		//this.supernetPrint.print();
	}
}
