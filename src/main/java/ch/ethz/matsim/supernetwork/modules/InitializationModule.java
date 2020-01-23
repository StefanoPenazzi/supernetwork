/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import ch.ethz.matsim.supernetwork.middlenetwork.MiddlenetworkFactory;
import ch.ethz.matsim.supernetwork.middlenetwork.MiddlenetworkFactoryImpl;
import ch.ethz.matsim.supernetwork.models.supernetwork_model.SupernetworkInitializationEvent;

/**
 * @author stefanopenazzi
 *
 */
public class InitializationModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		this.addControlerListenerBinding().to(SupernetworkInitializationEvent.class);
		
        bind(MiddlenetworkFactory.class).to(MiddlenetworkFactoryImpl.class);
        
        install(new ClusteringModule());
        install(new SubnetworkModule());
        install(new MiddlenetworkModule());
        install(new SupernetModule());
        install(new SimulationDataModule());
        
        
		
	}

}
