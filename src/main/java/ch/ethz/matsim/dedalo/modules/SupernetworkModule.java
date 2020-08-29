/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;
import org.matsim.core.controler.AbstractModule;

import com.google.inject.Singleton;

import ch.ethz.matsim.dedalo.supernetwork.events.SupernetworkUpdate;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkModule extends AbstractModule {

	public static final String STRATEGY_NAME = "Supernetwork";
	
	//@Inject
	//private SupernetworkConfigGroup supernetworkConfig;
	
	@Override
	public void install() {

		//initialization
		//Supernetwork works also without the ClusterRouting but this is faster for massive computation 
		install(new RoutingInitializationModule());
		install(new PlanOptimizationModule());
		
		//update
		bind(SupernetworkUpdate.class).in(Singleton.class);
		this.addControlerListenerBinding().to(SupernetworkUpdate.class);
	}
}
