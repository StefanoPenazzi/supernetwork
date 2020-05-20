/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkRoutingModule;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmOutput;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class ContainerManagerImpl implements ContainerManager {
	
	private final Map<String, UpdateAlgorithm> updateAlgorithmsMap;
	private final Map<String, SupernetworkRoutesContainer> containersMap;
	private final Map<String, TravelTime> travelTimes;
	private final SupernetworkRoutingModule supernetworkRoutingModule; 
	private List<Middlenetwork> middlenetworks;
	private  TreeMap<Coordin, Supernode> activitySupernodeMap = new TreeMap<Coordin, Supernode>();
	
	public ContainerManagerImpl(SupernetworkRoutingModule supernetworkRoutingModule,
			Map<String, UpdateAlgorithm> updateAlgorithmsMap,Map<String,
			SupernetworkRoutesContainer> containersMap,List<Middlenetwork> middlenetworks,
			Map<String, TravelTime> travelTimes) {
		this.supernetworkRoutingModule = supernetworkRoutingModule;
		this.updateAlgorithmsMap = updateAlgorithmsMap;
		this.containersMap = containersMap;
		this.middlenetworks = middlenetworks;
		this.travelTimes = travelTimes;
		initialize();
	}

	@Override
	public void updateContainer(String mode) { 
	  SupernetworkRoutesContainer src = containersMap.get(mode);
	  UpdateAlgorithm ua  = updateAlgorithmsMap.get(mode);
	  TravelTime tt = this.travelTimes.get(mode);
	  List<UpdateAlgorithmOutput> inputs = ua.getUpdate(src,middlenetworks,tt);
	  for (UpdateAlgorithmOutput mn: inputs) {
		  Path [] paths= this.supernetworkRoutingModule.calcTree(mn.getSupernode().getNode(), mn.getToNodes(),mn.getTime());
		  for(Path p: paths) {
			  src.add(mn.getSupernode(),Iterables.getLast(p.nodes),mn.getTime(),p);
		  }
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
	
	public void initialize() {
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
