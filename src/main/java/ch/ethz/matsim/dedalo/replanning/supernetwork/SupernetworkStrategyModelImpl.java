/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager.PlansForPopulationManager;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkStrategyModelImpl implements SupernetworkStrategyModel {
 
	private final PlansForPopulationManager plansForPopulationManager;
	
	public SupernetworkStrategyModelImpl(PlansForPopulationManager plansForPopulationManager) {
		this.plansForPopulationManager = plansForPopulationManager;
	}

	@Override
	public Plan newPlan(Person person) {
		return plansForPopulationManager.personNewPlan(person);
	}
}
