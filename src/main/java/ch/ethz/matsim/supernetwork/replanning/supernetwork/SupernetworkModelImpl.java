/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.supernetwork;

import java.util.List;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.gbl.Gbl;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkModelImpl implements SupernetworkModel {

	private final Network network;
	private final ContainerManager containerManager;
	private final PopulationFactory populationFactory;
	
	@Inject
	public SupernetworkModelImpl(Network network, ContainerManager containerManager,final PopulationFactory populationFactory) {
		Gbl.assertNotNull(network);
		this.network = network;
		this.containerManager = containerManager;
		this.populationFactory = populationFactory;
	}
	
	@Override
	public List<? extends PlanElement> newPlan(Plan plan) {
		// TODO Auto-generated method stub
		return null;
	}

}
