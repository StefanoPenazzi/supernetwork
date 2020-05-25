/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;
import org.matsim.core.controler.AbstractModule;
import ch.ethz.matsim.supernetwork.network.Network;
import ch.ethz.matsim.supernetwork.network.NetworkFactory;
import ch.ethz.matsim.supernetwork.network.NetworkFactoryImpl;
import ch.ethz.matsim.supernetwork.network.NetworkImpl;
import ch.ethz.matsim.supernetwork.network.utilities.SupernetPrint;
import ch.ethz.matsim.supernetwork.network.utilities.SupernetPrintImpl;


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
//		bind(Network.class).to(NetworkImpl.class).asEagerSingleton();
//		bind(NetworkFactory.class).to(NetworkFactoryImpl.class);
//		bind(SupernetPrint.class).to(SupernetPrintImpl.class);
		
        install(new ModulesSet());
	}
}
