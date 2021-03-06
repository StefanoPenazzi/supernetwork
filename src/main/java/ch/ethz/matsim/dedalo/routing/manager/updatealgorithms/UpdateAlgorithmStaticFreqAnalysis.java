/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager.updatealgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.dedalo.routing.manager.RoutesContainer;
import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateAlgorithmStaticFreqAnalysis implements UpdateAlgorithm {
	
	List<UpdateAlgorithmOutput> staticOutput = new ArrayList<>();
	double range = 600;
	
	@Override
	public List<UpdateAlgorithmOutput> getUpdate(RoutesContainer routesContainer,List<Middlenetwork> middlenetworks,
			TravelTime travelTimes) {
		
		if(routesContainer.empty()) {
			return initialUpdate(middlenetworks);
		}
		return runningUpdate(routesContainer,middlenetworks);
	}
	
	private List<UpdateAlgorithmOutput> initialUpdate(List<Middlenetwork> middlenetworks){
		
		for (Middlenetwork mn: middlenetworks) {
			Map<Integer,List<Node>> map=new HashMap<Integer,List<Node>>(); 
			List<ElementActivity> acts = mn.getCluster().getComponents();
			for(ElementActivity act: acts ) {
				int rr = (int)(act.getStartTime()/range);
				//System.out.println(act.getStartTime());
				if(map.get(rr)!=null) {
					map.get(rr).add(act.getToNode());
				}
				else {
					map.put(rr,new ArrayList());
					map.get(rr).add(act.getToNode());
				}
			}
			
			for (Integer key : map.keySet()) {
				//if(map.get(key).size() > 100) {
					UpdateAlgorithmOutput uao = new UpdateAlgorithmOutput(mn.getSuperNode(),map.get(key),key);
					staticOutput.add(uao);
				//}
			}
			
		}
		return staticOutput;
	}
	
	private List<UpdateAlgorithmOutput> runningUpdate(RoutesContainer routesContainer,List<Middlenetwork> middlenetworks){
		
		return staticOutput;
	}

}
