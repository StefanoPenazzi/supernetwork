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


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkStrategyProvider implements Provider<PlanStrategy> {

	private final GlobalConfigGroup globalConfigGroup;
	private final Provider<SupernetworkModel> supernetworkModelProvider;
	
	@Inject
	SupernetworkStrategyProvider(GlobalConfigGroup globalConfigGroup,Provider<SupernetworkModel> supernetworkModelProvider){
		this.globalConfigGroup = globalConfigGroup;
		this.supernetworkModelProvider = supernetworkModelProvider;
	}
	
	@Override
	public PlanStrategy get() {
		PlanStrategyImpl.Builder builder = new PlanStrategyImpl.Builder(new RandomPlanSelector<>());
		builder.addStrategyModule(new SupernetworkReplanningModule(globalConfigGroup,supernetworkModelProvider));
		return builder.build();
	}

}
