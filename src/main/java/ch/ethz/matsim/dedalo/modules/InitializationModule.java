/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;


/**
 * @author stefanopenazzi
 *
 */
public class InitializationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
        install(new PlanOptimizationModule());
	}

}
