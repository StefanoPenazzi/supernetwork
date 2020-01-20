package ch.ethz.matsim.supernetwork;

import org.matsim.analysis.ScoreStatsControlerListener;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.TerminationCriterion;
import org.matsim.core.scenario.ScenarioUtils;
import ch.ethz.matsim.utils.CommandLine;
import ch.ethz.matsim.utils.CommandLine.ConfigurationException;

import com.google.inject.Singleton;

import ch.ethz.matsim.supernetwork.halfnetwork.HalfnetworkFactory;
import ch.ethz.matsim.supernetwork.halfnetwork.HalfnetworkFactoryImpl;
import ch.ethz.matsim.supernetwork.model.ClusteringActivities;
import ch.ethz.matsim.supernetwork.modules.SupernetworkModule;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.LinkDataTTV;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainerDefaultImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelData;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TravelTime;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler.LinksTrafficFlowCollection;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.event_handler.LinksTrafficFlowComputation;



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
		
        final Config config = ConfigUtils.loadConfig(configFile);

        Scenario scenario = ScenarioUtils.loadScenario(config);

        //SupNetDefaultActivitiesAnalysis sn = new SupNetDefaultActivitiesAnalysis(scenario,outputPath);
        //ClusteringActivities ca = new ClusteringActivities(scenario,outputPath,cut);
        
        // controler
        Controler controler = new Controler(scenario);
        
        controler.addOverridingModule(new SupernetworkModule());
        
        //System.setProperty("scenario","sbb");
        
        controler.run();
	}
}
