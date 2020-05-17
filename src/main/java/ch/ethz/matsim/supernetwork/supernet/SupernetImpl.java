/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.facilities.ActivityFacility;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetImpl implements Supernet{

	private final static Logger log = Logger.getLogger(SupernetImpl.class);
	
	private  List<Middlenetwork> middlenetworks = null;
	private  ClustersContainer<ClusterActivitiesLocation,ElementActivity> clusterContainer = null;
	private  SupernetworkRoutingModule supernetworkRoutingModule = null;
	private  SupernetRoutesContainer supernetRoutesContainer;
	private  TreeMap<Coordin, Supernode> activitySupernodeMap = new TreeMap<Coordin, Supernode>();
	private int timeCounter = 1;
	
	
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
		for(Middlenetwork mn: this.middlenetworks) {
			List<ElementActivity> activities = mn.getCluster().getComponents();
			for(ElementActivity ea : activities) {
				Coordin c = new Coordin(ea.getActivity().getCoord());
				activitySupernodeMap.put(c,mn.getSuperNode());
			}
		}
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
	
		  if(this.middlenetworks != null && this.clusterContainer !=  null &&  this.supernetworkRoutingModule != null) {
		
		      double totTime = 0;
			
		      log.warn("inizio middlenetwork computation"); 
			  long startTimeT =  System.nanoTime();
			 
			  //here should be use something to understand which are the best new times and for which superodes and also which of the old do not need anymore
			  
			  for (Middlenetwork mn: this.middlenetworks) {
	        	  if( mn.getToNodes().size() > 0) {
	        		  Path [] paths= this.supernetworkRoutingModule.calcTree(mn.getSuperNode().getNode(), mn.getToNodes() ,timeCounter * 1000);
	        		  for(Path p: paths) {
	        			  supernetRoutesContainer.add(mn.getSuperNode(),Iterables.getLast(p.nodes),timeCounter * 1000,p );
	        		  }
	        	  }
			  }
			  timeCounter++;
			  long endTimeT = System.nanoTime();
			  log.warn("fine middlenetwork computation"); 
			  totTime = (double)(endTimeT - startTimeT);
			  
			  log.warn("time middlenetwork shortest tree: " + Double.toString(totTime/1000000000)); 
		  }
        
	}

	@Override
	public Supernode getSupernodeFromActivity(Activity act) {
		Coordin c = new Coordin(act.getCoord());
		return this.activitySupernodeMap.get(c);
	}

	@Override
	public Path getPathFromRoutesContainer(Activity act, Node toNode ,int time) {
		Supernode sn = getSupernodeFromActivity(act);
		if(sn != null) {
			return this.supernetRoutesContainer.getPath(sn,toNode ,time);
		}
		else {
			return null;
		}
	}
	
	class Coordin implements Comparable<Coordin>{

		private final double x;
		private final double y;
		
		public Coordin(Coord coord) {
			this.x =coord.getX();
			this.y=coord.getY();
		}
		
		public double getX() {
			return this.x;
		}
		
		public double getY() {
			return this.y;
		}
		
		@Override
		public int compareTo(Coordin coordinates) {
			if(coordinates.getX() > this.x) {
				return -1;
			}
			else if (coordinates.getX() < this.x){
				return 1;
			}
			else {
				if(coordinates.getY() > this.y) {
					return -1;
				}
				else if (coordinates.getY() < this.y){
					return 1;
				}
				else {
					return 0;
				}
			}
		}
		
	}
}
