/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateAlgorithmImpl implements UpdateAlgorithm {

	@Override
	public List<UpdateAlgorithmOutput> getUpdate(SupernetworkRoutesContainer supernetworkRoutesContainer,List<Middlenetwork> middlenetworks,
			TravelTime travelTimes) {
		
		if(supernetworkRoutesContainer.empty()) {
			return initialUpdate(middlenetworks);
		}
		return runningUpdate(supernetworkRoutesContainer,middlenetworks);
	}
	
	private List<UpdateAlgorithmOutput> initialUpdate(List<Middlenetwork> middlenetworks){
		List<UpdateAlgorithmOutput> output = new ArrayList<>();
		for (Middlenetwork mn: middlenetworks) {
			double time = 7200;
			for (int i =1;i<10;++i) {
				UpdateAlgorithmOutput uao = new UpdateAlgorithmOutput(mn.getSuperNode(),mn.getToNodes(),time*i);
				output.add(uao);
			}	
		}
		return output;
	}
	
	private List<UpdateAlgorithmOutput> runningUpdate(SupernetworkRoutesContainer supernetworkRoutesContainer,List<Middlenetwork> middlenetworks){
		List<UpdateAlgorithmOutput> output = new ArrayList<>();
		for (Middlenetwork mn: middlenetworks) {
			double time = 7200;
			for (int i =1;i<10;++i) {
				UpdateAlgorithmOutput uao = new UpdateAlgorithmOutput(mn.getSuperNode(),mn.getToNodes(),time*i);
				output.add(uao);
			}	
		}
		return output;
	}

}
