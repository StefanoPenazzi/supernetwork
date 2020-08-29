/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;

import org.matsim.core.controler.AbstractModule;

import ch.ethz.matsim.dedalo.mobsim.SupernetworkMobsimModule;

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
