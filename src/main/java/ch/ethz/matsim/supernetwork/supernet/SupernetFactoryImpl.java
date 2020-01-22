/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.Scenario;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.subnetwork.SubnetworkFromActivitiesCluster;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetFactoryImpl implements SupernetFactory {
	
	Supernet supernet;
	//clusterContainer is injected this means that is going to use the injected scenario and 
	//config files to be initialized. 
	//TODO what happens if the scenario is null etc
	ClustersContainer clusterContainer;
	
	Scenario scenario;
	
	SupernetworkConfigGroup supernetworkConfigGroup;
	
	@Inject
	public SupernetFactoryImpl(Supernet supernet, ClustersContainer clusterContainer, Scenario scenario) {//, SupernetworkConfigGroup supernetworkConfigGroup) {
		this.supernet = supernet;
		this.clusterContainer = clusterContainer;
		this.scenario = scenario;
		//this.supernetworkConfigGroup = supernetworkConfigGroup;
	}

	@Override
	public void create() {
		supernet.setActivitiesClustersContainer(this.clusterContainer);
		List<Subnetwork> subnetworks = new ArrayList<Subnetwork>();
		List<ClusterActivitiesLocation> lc = supernet.getActivitiesClusterContainer().getClusters();
		for(ClusterActivitiesLocation cdi: lc) {
			subnetworks.add(SubnetworkFromActivitiesCluster.fromActivitiesLocations(scenario.getNetwork(), cdi,0.9));
		}
	}
}
