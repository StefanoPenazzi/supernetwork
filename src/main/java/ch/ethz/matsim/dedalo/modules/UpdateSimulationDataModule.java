/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import com.google.inject.Singleton;

import ch.ethz.matsim.dedalo.supernetwork.events.SupernetworkUpdate;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateSimulationDataModule extends AbstractSupernetworkExtension {

	@Override
	protected void installExtension() {
		
		//bind(LinksTrafficFlowCollection.class).in(Singleton.class);
    	//bind(LinksTrafficFlowComputation.class).in(Singleton.class);
    	
        //this.addControlerListenerBinding().to(LinksTrafficFlowComputation.class);
        //this.addEventHandlerBinding().to(LinksTrafficFlowCollection.class);
		
		bind(SupernetworkUpdate.class).in(Singleton.class);
		this.addControlerListenerBinding().to(SupernetworkUpdate.class);
        
	}

}
