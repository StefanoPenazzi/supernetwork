/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;
import org.matsim.core.controler.AbstractModule;
import ch.ethz.matsim.supernetwork.supernet.Supernet;
import ch.ethz.matsim.supernetwork.supernet.SupernetFactory;
import ch.ethz.matsim.supernetwork.supernet.SupernetFactoryImpl;
import ch.ethz.matsim.supernetwork.supernet.SupernetImpl;
import ch.ethz.matsim.supernetwork.supernet.SupernetPrint;
import ch.ethz.matsim.supernetwork.supernet.SupernetPrintImpl;


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
		
		bind(Supernet.class).to(SupernetImpl.class).asEagerSingleton();
		bind(SupernetFactory.class).to(SupernetFactoryImpl.class);
		bind(SupernetPrint.class).to(SupernetPrintImpl.class);
		
        install(new ModulesSet());
	}
}
