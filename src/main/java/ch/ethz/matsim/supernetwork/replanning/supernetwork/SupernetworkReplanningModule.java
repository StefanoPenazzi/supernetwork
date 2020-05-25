/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.supernetwork;

import org.matsim.core.config.groups.GlobalConfigGroup;
import org.matsim.core.population.algorithms.PlanAlgorithm;
import org.matsim.core.replanning.modules.AbstractMultithreadedModule;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkReplanningModule extends AbstractMultithreadedModule {

	/**
	 * @param globalConfigGroup
	 */
	public SupernetworkReplanningModule(GlobalConfigGroup globalConfigGroup) {
		super(globalConfigGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlanAlgorithm getPlanAlgoInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
