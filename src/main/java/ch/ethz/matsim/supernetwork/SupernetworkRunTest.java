package ch.ethz.matsim.supernetwork;

import org.matsim.analysis.ScoreStatsControlerListener;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.TerminationCriterion;
import org.matsim.core.scenario.ScenarioUtils;

import com.google.inject.Singleton;

public class SupernetworkRunTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				System.setProperty("matsim.preferLocalDtds", "true");
				
		        final Config config = ConfigUtils.loadConfig("/home/stefanopenazzi/git/supernetwork/input/siouxfalls-2014/config_default.xml");

		        Scenario scenario = ScenarioUtils.loadScenario(config);

		        // controler
		        Controler controler = new Controler(scenario);
		        
		        System.setProperty("scenario","sbb");
		        
		        controler.run();
	}
}
