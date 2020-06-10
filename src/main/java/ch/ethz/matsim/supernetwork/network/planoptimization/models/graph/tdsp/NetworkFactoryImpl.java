/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.scoring.functions.ScoringParameters;
import org.matsim.core.scoring.functions.ScoringParametersForPerson;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;

/**
 * @author stefanopenazzi
 *
 */
public class NetworkFactoryImpl implements NetworkFactory {

	private final ContainerManager containerManager;
	private final ScoringParametersForPerson params;
	
	@Inject
	public NetworkFactoryImpl(ContainerManager containerManager,ScoringParametersForPerson params) {
		this.containerManager = containerManager;
		this.params = params;
	}
	
	@Override
	public Network createNetwork(Plan plan) {
		
		final ScoringParameters parameters = params.getScoringParameters(plan.getPerson());
		
		// the first node should represent some sort of starting time from the first activity?
		
		for(PlanElement pe: plan.getPlanElements()) {
			if(pe instanceof Activity) {
				// create new nodes respecting the constraints
			}
		}
		
		return new NetworkImpl(containerManager);
	}
	
	

}
