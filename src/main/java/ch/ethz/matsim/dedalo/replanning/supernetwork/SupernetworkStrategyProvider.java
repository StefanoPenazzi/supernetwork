/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.replanning.PlanStrategy;
import org.matsim.core.replanning.PlanStrategyImpl;
import org.matsim.core.replanning.selectors.RandomPlanSelector;
import com.google.inject.Inject;
import com.google.inject.Provider;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.containers.PlansForPopulationContainer;


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
		PlanStrategyImpl.Builder builder = new PlanStrategyImpl.Builder(new RandomPlanSelector<>());
		builder.addStrategyModule(new SupernetworkReplanningModule(globalConfigGroup,supernetworkStrategyModelProvider));
		return builder.build();
	}

}
