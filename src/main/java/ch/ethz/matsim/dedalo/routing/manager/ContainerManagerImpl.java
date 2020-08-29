/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.javatuples.Pair;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;
import org.matsim.facilities.Facility;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.routing.manager.RoutesContainer;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithmOutput;
import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerImpl implements ContainerManager {
	
	private final Map<String, UpdateAlgorithm> updateAlgorithmsMap;
	private final Map<String, RoutesContainer> containersMap;
	private final Map<String, TravelTime> travelTimes;
	private final RoutingManagerFactory routingManagerFactory; 
	private RoutingManager routingManager;
	private List<Middlenetwork> middlenetworks;
	private  TreeMap<Coordin, Supernode> activitySupernodeMap = new TreeMap<Coordin, Supernode>();
	private final ActivityFacilities facilities;
	private final Network network;
	
	@Inject
	public ContainerManagerImpl(RoutingManagerFactory routingManagerFactory,
			Map<String, UpdateAlgorithm> updateAlgorithmsMap,Map<String,
			RoutesContainer> containersMap,
			Map<String, TravelTime> travelTimes,ActivityFacilities facilities,
			Network network) {
		this.routingManagerFactory = routingManagerFactory;
		this.updateAlgorithmsMap = updateAlgorithmsMap;
		this.containersMap = containersMap;
		this.middlenetworks = null;
		this.travelTimes = travelTimes;
		this.facilities = facilities;
		this.network= network;
		
	}

	@Override
	public void updateContainer(String mode) { 
	  RoutesContainer src = containersMap.get(mode);
	  UpdateAlgorithm ua  = updateAlgorithmsMap.get(mode);
	  TravelTime tt = this.travelTimes.get(mode);
	  List<UpdateAlgorithmOutput> inputs = ua.getUpdate(src,this.middlenetworks,tt);
	  routingManager.init();
	  for (UpdateAlgorithmOutput uao: inputs) {
		  routingManager.addRequest(uao);
//		  Path [] paths= this.supernetworkRoutingModule.calcTree(mn.getSupernode().getNode(), mn.getToNodes(),mn.getTime());
//		  for(Path p: paths) {
//			  src.add(mn.getSupernode(),Iterables.getLast(p.nodes),mn.getTime(),p);
//		  }
		  
	  }	
	  List<Pair<PathTimeKey,Path>> res = routingManager.run();
	  for(Pair<PathTimeKey,Path> p: res) {
		  src.add(p.getValue0().getFromNode(),Iterables.getLast(p.getValue1().nodes),p.getValue0().getTime(),p.getValue1());
	  }
	}
	
	@Override
	public Path getPath(Node fromNode, Node toNode ,double time,String mode) {
		if(fromNode == null || toNode == null || mode != "car") {
			System.out.println();
		}
		return containersMap.get(mode).getPath(fromNode, toNode, time);
	}
	
	@Override 
	public List<Middlenetwork> getMiddlenetworks(){
		return this.middlenetworks;
	}
	
	@Override
	public void setMiddlenetworks(List<Middlenetwork> middlenetworks) {
		this.middlenetworks = middlenetworks;
		initialize();
	}
	
	public void initialize() {
		
		routingManager = routingManagerFactory.createRoutingManager(middlenetworks);
		
		for(Middlenetwork mn: this.middlenetworks) {
			List<ElementActivity> activities = mn.getCluster().getComponents();
			for(ElementActivity ea : activities) {
				Coordin c = new Coordin(ea.getFacility().getCoord());
				activitySupernodeMap.put(c,mn.getSuperNode());
			}
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
