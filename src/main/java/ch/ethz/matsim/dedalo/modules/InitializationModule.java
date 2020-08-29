/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import ch.ethz.matsim.dedalo.routing.network.cluster.models.supernetwork_model.SupernetworkInitializationEvent;
import ch.ethz.matsim.dedalo.supernetwork.utilities.ActivityManagerImpl;


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
