/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.population.algorithms.PlanAlgorithm;
import org.matsim.core.replanning.modules.AbstractMultithreadedModule;
import com.google.inject.Provider;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkReplanningModule extends AbstractMultithreadedModule {

	private final Provider<SupernetworkStrategyModel> supernetworkStrategyModelProvider;
	
	/**
	 * @param globalConfigGroup
	 */
	public SupernetworkReplanningModule(GlobalConfigGroup globalConfigGroup, Provider<SupernetworkStrategyModel> supernetworkStrategyModelProvider) {
		super(globalConfigGroup);
		this.supernetworkStrategyModelProvider =  supernetworkStrategyModelProvider;
	}

	@Override
	public PlanAlgorithm getPlanAlgoInstance() {
		SupernetworkStrategyModel supernetworkStrategyModel =  this.supernetworkStrategyModelProvider.get();
		return new SupernetworkModelAlgorithm(supernetworkStrategyModel);
	}

}
