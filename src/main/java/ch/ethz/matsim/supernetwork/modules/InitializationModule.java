/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.network.utilities.ActivityManagerImpl;
import ch.ethz.matsim.supernetwork.routing.network.cluster.models.supernetwork_model.SupernetworkInitializationEvent;


/**
 * @author stefanopenazzi
 *
 */
public class InitializationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		this.addControlerListenerBinding().to(SupernetworkInitializationEvent.class);
		bindActivityManager().to(ActivityManagerImpl.class).asEagerSingleton();

        
        install(new ClusteringModule());
        install(new SubnetworkModule());
        install(new MiddlenetworkModule());
        install(new SupernetworkContainerModule());
        install(new PlanOptimizationModule());
	}

}
