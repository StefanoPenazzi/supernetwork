/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.router;

import java.util.List;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.dedalo.routing.router.LeastCostTreeCalculator;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkRoutingModuleImpl implements SupernetworkRoutingModule {

	private final String mode;
	private final SupernetworkLeastCostTreeCalculatorFactory supernetworkLeastCostTreeCalculatorFactory;


	 public SupernetworkRoutingModuleImpl(
			final String mode,
			final SupernetworkLeastCostTreeCalculatorFactory supernetworkLeastCostTreeCalculatorFactory) {
		 
		 this.supernetworkLeastCostTreeCalculatorFactory = supernetworkLeastCostTreeCalculatorFactory;
		 this.mode = mode;
	}
	
	public Path[] calcTree(final Node root, List<Node> toNodes ,final double departureTime) {		
		
		LeastCostTreeCalculator leastCostTreeCalculator = supernetworkLeastCostTreeCalculatorFactory.createTreeCalculator("car");
		Path[] paths = leastCostTreeCalculator.calcLeastCostTree(root, toNodes ,departureTime);
		//first link and node are the middlelink and supernode this are not considered  in path
		for(Path p: paths) {
			if(p.links.size() >0) {
				p.links.remove(0);
				p.nodes.remove(0);
			}
			else
			{
				p = null;
			}
		}
		return paths;
	}

//	@Override
//	public Path calcPathFromTree(Node toNode, double startTime, PredecessorNode[] pn) {
//		
//		return treeAlgo.calcLeastCostPathFromPredecessorNode(toNode, startTime, pn);
//	}
}