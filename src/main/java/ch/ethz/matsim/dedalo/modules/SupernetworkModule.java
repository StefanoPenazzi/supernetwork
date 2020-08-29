/**
 * 
 */
package ch.ethz.matsim.dedalo.modules;
import org.matsim.core.controler.AbstractModule;


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
