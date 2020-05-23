/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javatuples.Pair;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms.UpdateAlgorithmOutput;

/**
 * @author stefanopenazzi
 *
 */
public interface RoutingManager {

	public void init();
	public List<Pair<PathTimeKey,Path>> run();
	public void addRequest(UpdateAlgorithmOutput uao);
}
