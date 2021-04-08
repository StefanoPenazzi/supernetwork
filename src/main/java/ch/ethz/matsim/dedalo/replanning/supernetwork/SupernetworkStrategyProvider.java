/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.replanning.PlanStrategy;
import org.matsim.core.replanning.PlanStrategyImpl;
import org.matsim.core.replanning.selectors.RandomPlanSelector;
import com.google.inject.Inject;
import com.google.inject.Provider;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkStrategyProvider implements Provider<PlanStrategy> {

	private final GlobalConfigGroup globalConfigGroup;
	private final Provider<SupernetworkStrategyModel> supernetworkStrategyModelProvider;
	
	@Inject
	SupernetworkStrategyProvider(GlobalConfigGroup globalConfigGroup,
			Provider<SupernetworkStrategyModel> supernetworkStrategyModelProvider){
		this.globalConfigGroup = globalConfigGroup;
		this.supernetworkStrategyModelProvider = supernetworkStrategyModelProvider;
	}
	
	@Override
	public PlanStrategy get() {
		PlanStrategyImpl.Builder builder = new PlanStrategyImpl.Builder(new RandomPlanSelector<Plan,Person>());
		builder.addStrategyModule(new SupernetworkReplanningModule(globalConfigGroup,supernetworkStrategyModelProvider));
		return builder.build();
	}

}
