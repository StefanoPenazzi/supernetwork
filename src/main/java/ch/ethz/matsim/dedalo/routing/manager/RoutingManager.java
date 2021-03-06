/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import java.util.List;
import org.javatuples.Pair;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithmOutput;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutingManager {

	public void init();
	public List<Pair<PathTimeKey,Path>> run();
	public void addRequest(UpdateAlgorithmOutput uao);
}
