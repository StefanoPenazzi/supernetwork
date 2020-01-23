/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;

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
