/**
 * 
 */
package ch.ethz.matsim.dedalo.mobsim;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.mobsim.qsim.qnetsimengine.ConfigurableQNetworkFactory;
import org.matsim.core.mobsim.qsim.qnetsimengine.QNetworkFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author stefanopenazzi
 *
 */
public class ConfigurableQNetworkFactoryProvider implements Provider<QNetworkFactory>{

	private final EventsManager events;
	private final Scenario scenario;
	
	@Inject
	public ConfigurableQNetworkFactoryProvider(EventsManager events,Scenario scenario) {
		this.events = events;
		this.scenario = scenario;
	}
	
	@Override
	public QNetworkFactory get() {
		ConfigurableQNetworkFactory qnf = new ConfigurableQNetworkFactory(this.events, this.scenario);
		qnf.setTurnAcceptanceLogic(new SupernetworkTurnAcceptanceLogic());
		return qnf;
	}
	
}