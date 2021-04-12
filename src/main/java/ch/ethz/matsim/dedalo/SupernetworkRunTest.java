package ch.ethz.matsim.dedalo;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;


import ch.ethz.matsim.dedalo.replanning.supernetwork.SupernetworkStrategyModule;
import ch.ethz.matsim.utils.CommandLine;
import ch.ethz.matsim.utils.CommandLine.ConfigurationException;




public class SupernetworkRunTest {
	public static void main(String[] args) throws ConfigurationException {
		// TODO Auto-generated method stub
		
		CommandLine cmd = new CommandLine.Builder(args)
                .allowOptions("configPath", "output", "cut")
                .build();
		
		final String configFile = cmd.getOption("configPath").orElse("..\\input\\CNB\\config\\config_parsed.xml");
		String outputPath = cmd.getOption("output").orElse("output_sbb_dmc");
		double cut = cmd.getOption("cut").map(Double::parseDouble).orElse(0.0);
		
		System.setProperty("matsim.preferLocalDtds", "true");
		
		//RegionHierarchicalCSConfigGroup rh = new RegionHierarchicalCSConfigGroup();
		//rh.setCut((int)cut);
		
        final Config config = ConfigUtils.loadConfig(configFile);
        //config.plansCalcRoute().setInsertingAccessEgressWalk(true);

        Scenario scenario = ScenarioUtils.loadScenario(config);

        //SupNetDefaultActivitiesAnalysis sn = new SupNetDefaultActivitiesAnalysis(scenario,outputPath);
        //ClusteringActivities ca = new ClusteringActivities(scenario,outputPath,cut);
        
        // controler
        Controler controler = new Controler(scenario);
        
        //routing
        controler.addOverridingModule(new ClusterRoutingModule());
        //controler.addOverridingQSimModule(new SupernetworkMobsimModule());
        
        //supernetwork
        //controler.addOverridingModule(new SupernetworkModule());
        
        //supernetworkStrategy
        //controler.addOverridingModule(new SupernetworkStrategyModule());
        
        //System.setProperty("scenario","sbb");
        
        controler.run();
	}
}
