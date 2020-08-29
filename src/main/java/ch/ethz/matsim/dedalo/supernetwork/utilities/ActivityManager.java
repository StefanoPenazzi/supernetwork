/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.utilities;

import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public interface ActivityManager {

	public Node ActivityFromNode(Activity activity);
	public Node ActivityToNode(Activity activity);
}
