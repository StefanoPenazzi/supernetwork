/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;

/**
 * @author stefanopenazzi
 *
 */
public class modulesSet extends AbstractModule {

	@Override
	public void install() {
		install(new ClusteringModule());
		
	}

}
