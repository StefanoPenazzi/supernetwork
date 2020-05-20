/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;

import ch.ethz.matsim.supernetwork.mobsim.SupernetworkMobsimModule;

/**
 * @author stefanopenazzi
 *
 */
public class ModulesSet extends AbstractModule {

	@Override
	public void install() {
		install(new InitializationModule());
		install(new UpdateSimulationDataModule());
	}

}
