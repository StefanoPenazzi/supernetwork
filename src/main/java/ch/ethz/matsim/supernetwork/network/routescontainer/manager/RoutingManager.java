/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javatuples.Pair;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithmOutput;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutingManager {

	public void init();
	public List<Pair<PathTimeKey,Path>> run();
	public void addRequest(UpdateAlgorithmOutput uao);
}
