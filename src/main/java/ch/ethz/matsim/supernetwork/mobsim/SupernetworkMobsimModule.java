/**
 * 
 */
package ch.ethz.matsim.supernetwork.mobsim;

import org.matsim.core.mobsim.qsim.AbstractQSimModule;
import org.matsim.core.mobsim.qsim.qnetsimengine.DefaultQNetworkFactory;
import org.matsim.core.mobsim.qsim.qnetsimengine.QNetworkFactory;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkMobsimModule extends AbstractQSimModule {

	@Override
	protected void configureQSim() {
	     bind(QNetworkFactory.class).toProvider(ConfigurableQNetworkFactoryProvider.class);
	}
}
