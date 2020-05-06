/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.Dijkstra;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetImpl implements Supernet{

	private final static Logger log = Logger.getLogger(SupernetImpl.class);
	
	private  List<Middlenetwork> middlenetworks;
	private  ClustersContainer<ClusterActivitiesLocation,ElementActivity> clusterContainer;
	private  SupernetworkRoutingModule supernetworkRoutingModule;
	private  SupernetRoutesContainer supernetRoutesContainer;
	 
	
	
	@Inject
	public SupernetImpl(SupernetRoutesContainer supernetRoutesContainer) {
		this.supernetRoutesContainer = supernetRoutesContainer;
	}
	
	@Override
	public List<Middlenetwork> getMiddlenetworks() {
		
		return this.middlenetworks;
	}

	@Override
	public void setActivitiesClustersContainer(ClustersContainer<ClusterActivitiesLocation,ElementActivity> cc) {
		this.clusterContainer = cc ;
	}

	@Override
	public void setMiddlenetworks(List<Middlenetwork> hf) {
		this.middlenetworks = hf;
	}

	@Override
	public ClustersContainer<ClusterActivitiesLocation,ElementActivity> getActivitiesClusterContainer() {
		
		return this.clusterContainer;
	}

	@Override
	public void setSupernetworkRoutingModule(SupernetworkRoutingModule supernetworkRoutingModule) {
		this.supernetworkRoutingModule = supernetworkRoutingModule;
		
	}

	@Override
	public void treesCalculation() {
	
	      double totTime = 0;
		
	      log.warn("inizio middlenetwork computation"); 
		  long startTimeT =  System.nanoTime();
		 
		  for (Middlenetwork mn: this.middlenetworks) {
        	  if( mn.getToNodes().size() > 0) {
				  this.supernetworkRoutingModule.calcTree(mn.getSuperNode().getNode(), mn.getToNodes() ,10);
        	  }
		  }
		  
		  long endTimeT = System.nanoTime();
		  log.warn("fine middlenetwork computation"); 
		  totTime = (double)(endTimeT - startTimeT);
		  
		  log.warn("time middlenetwork shortest tree: " + Double.toString(totTime/1000000000)); 
        
	}
}
