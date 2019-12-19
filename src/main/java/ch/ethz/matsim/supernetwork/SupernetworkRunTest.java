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

import ch.ethz.matsim.supernetwork.utilities.analysis.inputData.SupNetDefaultActivitiesAnalysis;

public class SupernetworkRunTest {
	public static void main(String[] args) throws ConfigurationException {
		// TODO Auto-generated method stub
		
		CommandLine cmd = new CommandLine.Builder(args)
                .allowOptions("configPath", "output")
                .build();
		
		final String configFile = cmd.getOption("configPath").orElse("..\\input\\CNB\\config\\config_parsed.xml");
		String outputPath = cmd.getOption("output").orElse("output_sbb_dmc");
		
		System.setProperty("matsim.preferLocalDtds", "true");
		
        final Config config = ConfigUtils.loadConfig(configFile);

        Scenario scenario = ScenarioUtils.loadScenario(config);

        SupNetDefaultActivitiesAnalysis sn = new SupNetDefaultActivitiesAnalysis(scenario,outputPath);
        
        // controler
        //Controler controler = new Controler(scenario);
        
        //System.setProperty("scenario","sbb");
        
        //controler.run();
	}
}
