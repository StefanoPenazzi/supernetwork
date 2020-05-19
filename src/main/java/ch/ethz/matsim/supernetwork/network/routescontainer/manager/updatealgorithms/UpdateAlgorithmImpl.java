/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateAlgorithmImpl implements UpdateAlgorithm {

	@Override
	public List<UpdateAlgorithmOutput> getUpdate(SupernetworkRoutesContainer supernetworkRoutesContainer) {
		
		if(supernetworkRoutesContainer.empty()) {
			return initialUpdate();
		}
		return runningUpdate(supernetworkRoutesContainer);
	}
	
	private List<UpdateAlgorithmOutput> initialUpdate(){
		List<UpdateAlgorithmOutput> output = new ArrayList<>();
		
		
		return output;
	}
	
	private List<UpdateAlgorithmOutput> runningUpdate(SupernetworkRoutesContainer supernetworkRoutesContainer){
		List<UpdateAlgorithmOutput> output = new ArrayList<>();
		
		
		return output;
	}

}
