/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.supernetwork;

import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.population.algorithms.PlanAlgorithm;
import org.matsim.facilities.ActivityFacilities;

import com.google.inject.Provider;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.replanning.rerouting.ClustersReRouteModel;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkModelAlgorithm implements PlanAlgorithm{

	private final SupernetworkModel supernetworkModel;
	
	public SupernetworkModelAlgorithm(SupernetworkModel supernetworkModel) {
		this.supernetworkModel =  supernetworkModel;
		
	}
	
	
	@Override
	public void run(Plan plan) {
		// TODO Auto-generated method stub
		
	}

}
