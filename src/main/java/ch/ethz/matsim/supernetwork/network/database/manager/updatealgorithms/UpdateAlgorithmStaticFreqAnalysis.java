/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.matsim.core.router.util.TravelTime;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.network.database.containers.RoutesContainer;
import ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateAlgorithmStaticFreqAnalysis implements UpdateAlgorithm {
	
	List<UpdateAlgorithmOutput> staticOutput = new ArrayList<>();
	double range = 3600;
	
	@Override
	public List<UpdateAlgorithmOutput> getUpdate(RoutesContainer supernetworkRoutesContainer,List<Middlenetwork> middlenetworks,
			TravelTime travelTimes) {
		
		if(supernetworkRoutesContainer.empty()) {
			return initialUpdate(middlenetworks);
		}
		return runningUpdate(supernetworkRoutesContainer,middlenetworks);
	}
	
	private List<UpdateAlgorithmOutput> initialUpdate(List<Middlenetwork> middlenetworks){
		
		int[] freq = new int[(int)(86400/range)];
		Arrays.fill(freq,0);
		
		for (Middlenetwork mn: middlenetworks) {
			List<ElementActivity> acts = mn.getCluster().getComponents();
			for(ElementActivity act: acts ) {
				int rr = (int)(act.getActivity().getStartTime()/range);
				freq[rr] = freq[rr]+1;
			}
			for(int i = 0;i<freq.length;i++) {
				if(freq[i] > 0) {
					UpdateAlgorithmOutput uao = new UpdateAlgorithmOutput(mn.getSuperNode(),mn.getToNodes(),range*i);
					staticOutput.add(uao);
				}
			}
			
			
//			double time = 7200;
//			for (int i =1;i<10;++i) {
//				UpdateAlgorithmOutput uao = new UpdateAlgorithmOutput(mn.getSuperNode(),mn.getToNodes(),time*i);
//				staticOutput.add(uao);
//			}	
			
		}
		return staticOutput;
	}
	
	private List<UpdateAlgorithmOutput> runningUpdate(RoutesContainer supernetworkRoutesContainer,List<Middlenetwork> middlenetworks){
		
		return staticOutput;
	}

}