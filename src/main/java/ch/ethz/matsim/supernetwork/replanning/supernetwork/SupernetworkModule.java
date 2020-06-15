/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.supernetwork;

import org.matsim.core.controler.AbstractModule;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;



/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkModule extends AbstractModule{
	
	public static final String STRATEGY_NAME = "Supernetwork";
	
	@Override
	public void install() {
		//For a strategy
		addPlanStrategyBinding(STRATEGY_NAME).toProvider(SupernetworkStrategyProvider.class);
		//bind(SupernetworkModel.class).to(TdspOrdaRom.class);
		bind(ScoringFunctionsForPopulationGraph.class).asEagerSingleton();
		
		install(new ch.ethz.matsim.supernetwork.modules.SupernetworkModule());
		
	}
}
