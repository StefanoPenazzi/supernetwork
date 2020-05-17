/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import com.google.inject.Singleton;

import ch.ethz.matsim.supernetwork.network.events.SupernetContainerFilling;

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
		
		bind(SupernetContainerFilling.class).in(Singleton.class);
		this.addControlerListenerBinding().to(SupernetContainerFilling.class);
        
	}

}
