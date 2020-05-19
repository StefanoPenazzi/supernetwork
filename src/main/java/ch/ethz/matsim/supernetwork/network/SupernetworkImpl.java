/**
 * 
 */
package ch.ethz.matsim.supernetwork.network;

import java.util.List;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmOutput;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkImpl implements Supernetwork{

	private final static Logger log = Logger.getLogger(SupernetworkImpl.class);
	
	private  List<Middlenetwork> middlenetworks = null;
	private  ClustersContainer<ClusterActivitiesLocation,ElementActivity> clusterContainer = null;
	private  ContainerManager containerManager;
	private  TreeMap<Coordin, Supernode> activitySupernodeMap = new TreeMap<Coordin, Supernode>();
	
	
	@Inject
	public SupernetworkImpl() { //tenere solo container managare routesContainer e injectato dentro a container manager
		
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
	public void containerUpdate() {
		  if(this.middlenetworks != null && this.clusterContainer !=  null) {
			  containerManager.updateContainer("Car");
		  }
	}

	@Override
	public Supernode getSupernodeFromActivity(Activity act) {
		Coordin c = new Coordin(act.getCoord());
		return this.activitySupernodeMap.get(c);
	}

	@Override
	public Path getPath(Activity act, Node toNode ,int time,String mode) {
		Supernode sn = getSupernodeFromActivity(act);
		if(sn != null) {
			return this.containerManager.getPath(sn,toNode ,time,mode);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void setContainerManager(ContainerManager containerManager) {
		this.containerManager = containerManager;
		
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
