/**
 * 
 */
package ch.ethz.matsim.dedalo.replanning.supernetwork;

import org.matsim.core.controler.AbstractModule;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;



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
		
		install(new ch.ethz.matsim.dedalo.modules.SupernetworkModule());
		
	}
}
