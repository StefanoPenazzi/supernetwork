/**
 * 
 */
package ch.ethz.matsim.supernetwork.algorithms.router.shortest_path;

import java.util.Arrays;
import java.util.List;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.population.routes.RouteUtils;
import org.matsim.core.router.EmptyStageActivityTypes;
import org.matsim.core.router.RoutingModule;
import org.matsim.core.router.StageActivityTypes;
import org.matsim.core.router.util.LeastCostPathCalculator;
import org.matsim.core.router.util.NodeData;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.facilities.Facility;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.LeastCostTreeCalculator;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkRoutingModuleImpl implements SupernetworkRoutingModule {

	private final String mode;
	private final PopulationFactory populationFactory;

	private final Network network;
	private final LeastCostTreeCalculator treeAlgo;


	 public SupernetworkRoutingModuleImpl(
			final String mode,
			final PopulationFactory populationFactory,
			final Network network,
			final LeastCostTreeCalculator treeAlgo) {
		 Gbl.assertNotNull(network);
		 this.network = network;
		 this.treeAlgo = treeAlgo;
		 this.mode = mode;
		 this.populationFactory = populationFactory;
	}
	
	public Path[] calcTree(final Node root, List<Node> toNodes ,final double departureTime) {		
		
		Path[] paths = treeAlgo.calcLeastCostTree(root, toNodes ,departureTime);
		//first link and node are the middlelink and supernode this are not considered  in path
		for(Path p: paths) {
			p.links.remove(0);
			p.nodes.remove(0);
		}
		return paths;
	}

//	@Override
//	public Path calcPathFromTree(Node toNode, double startTime, PredecessorNode[] pn) {
//		
//		return treeAlgo.calcLeastCostPathFromPredecessorNode(toNode, startTime, pn);
//	}
}