/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import org.matsim.core.controler.AbstractModule;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.FastDijkstraShortestTreeFactory;
import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.SupernetworkLeastCostTreeCalculatorFactory;


/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkLeastCostTreeCalculatorModule extends AbstractModule  {
	
	 @Override
	    public void install() {

		 bind(SupernetworkLeastCostTreeCalculatorFactory.class).to(FastDijkstraShortestTreeFactory.class);
	        
	    }

}
