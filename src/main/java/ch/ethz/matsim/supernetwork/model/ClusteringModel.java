/**
 * 
 */
package ch.ethz.matsim.supernetwork.model;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.controler.events.BeforeMobsimEvent;
import org.matsim.core.controler.listener.BeforeMobsimListener;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;

/**
 * @author stefanopenazzi
 *
 */
public class ClusteringModel implements BeforeMobsimListener {

	Scenario scenario;
	
	@Inject
	ClusteringModel (Scenario scenario){
		this.scenario = scenario;
	}
	
	
	@Override
	public void notifyBeforeMobsim(BeforeMobsimEvent event) {

		ClustersContainer ca = ClusteringActivities.getClustersContainer(scenario,"home/stefanopenazzi/git/supernetwork/output",1000);
	}

}
