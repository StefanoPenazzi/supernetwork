/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;
import org.matsim.core.controler.AbstractModule;
import ch.ethz.matsim.supernetwork.network.Supernetwork;
import ch.ethz.matsim.supernetwork.network.SupernetworkFactory;
import ch.ethz.matsim.supernetwork.network.SupernetworkFactoryImpl;
import ch.ethz.matsim.supernetwork.network.SupernetworkImpl;
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
		// remove all the other replanning strategies
		//supernetwork requires a step size phase 
		
		bind(Supernetwork.class).to(SupernetworkImpl.class).asEagerSingleton();
		bind(SupernetworkFactory.class).to(SupernetworkFactoryImpl.class);
		bind(SupernetPrint.class).to(SupernetPrintImpl.class);
		
        install(new ModulesSet());
	}
}
