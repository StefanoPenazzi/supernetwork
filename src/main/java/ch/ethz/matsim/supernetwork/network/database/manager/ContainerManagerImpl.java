/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.javatuples.Pair;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.network.database.containers.RoutesContainer;
import ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms.UpdateAlgorithmOutput;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

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
	
	@Inject
	public ContainerManagerImpl(RoutingManagerFactory routingManagerFactory,
			Map<String, UpdateAlgorithm> updateAlgorithmsMap,Map<String,
			RoutesContainer> containersMap,
			Map<String, TravelTime> travelTimes) {
		this.routingManagerFactory = routingManagerFactory;
		this.updateAlgorithmsMap = updateAlgorithmsMap;
		this.containersMap = containersMap;
		this.middlenetworks = null;
		this.travelTimes = travelTimes;
		
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
	  List<Pair<PathTimeKey,Path>> res;
	  res = routingManager.run();
	  for(Pair<PathTimeKey,Path> p: res) {
		  src.add(p.getValue0().getSupernode(),Iterables.getLast(p.getValue1().nodes),p.getValue0().getTime(),p.getValue1());
	  }
	  System.out.println("---");
	}
	
	@Override
	public Path getPath(Supernode supernode, Node toNode ,double time,String mode) {
		return containersMap.get(mode).getPath(supernode, toNode, time);
	}
	
	@Override
	public Path getPath(Activity activity, Node toNode ,double time,String mode) {
		Coordin c = new Coordin(activity.getCoord());
		Supernode sn = this.activitySupernodeMap.get(c);
		if(sn == null) {
			return null;
		}
		return getPath(this.activitySupernodeMap.get(c),toNode,time,mode);
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
				Coordin c = new Coordin(ea.getActivity().getCoord());
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
