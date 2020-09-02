/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager.updatealgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.dedalo.routing.manager.RoutesContainer;
import ch.ethz.matsim.dedalo.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;
import ch.ethz.matsim.dedalo.routing.network.cluster.elements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateAlgorithmDynamicFreqAnalysis implements UpdateAlgorithm {
	
	List<UpdateAlgorithmOutput> staticOutput = new ArrayList<>();
	double range = 6000;
	
	@Override
	public List<UpdateAlgorithmOutput> getUpdate(RoutesContainer routesContainer,List<Middlenetwork> middlenetworks,
			TravelTime travelTimes) {
		
		if(routesContainer.empty()) {
			return initialUpdate(middlenetworks);
		}
		return runningUpdate(routesContainer,middlenetworks);
	}
	
	private List<UpdateAlgorithmOutput> initialUpdate(List<Middlenetwork> middlenetworks){
		
		int[] freq = new int[(int)(86400/range)];
		Arrays.fill(freq,0);
		
		for (Middlenetwork mn: middlenetworks) {
			List<ElementActivity> acts = mn.getCluster().getComponents();
			for(ElementActivity act: acts ) {
				int rr = (int)(act.getStartTime()/range);
				freq[rr] = freq[rr]+1;
			}
			for(int i = 0;i<freq.length;i++) {
				//if(freq[i] > 0) {
					UpdateAlgorithmOutput uao = new UpdateAlgorithmOutput(mn.getSuperNode(),mn.getToNodes(),range*i);
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