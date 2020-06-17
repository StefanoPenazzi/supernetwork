/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.networkmodels.supernetwork_model.SupernetworkInitializationEvent;

/**
 * @author stefanopenazzi
 *
 */
public class InitializationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		this.addControlerListenerBinding().to(SupernetworkInitializationEvent.class);
        
        install(new ClusteringModule());
        install(new SubnetworkModule());
        install(new MiddlenetworkModule());
        install(new SupernetworkContainerModule());
        install(new PlanOptimizationModule());
	}

}
