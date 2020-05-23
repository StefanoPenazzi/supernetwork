/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.containers;


import java.util.TreeMap;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.network.database.manager.PathTimeKey;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class RoutesContainerImpl implements RoutesContainer{

	private TreeMap<PathTimeKey,Path> container = new TreeMap<>();
	
	@Inject
	public RoutesContainerImpl() {
		
	}
	
	@Override
	public void add(Supernode supernode, Node toNode, double time,Path ln) {
		container.put(new PathTimeKey(supernode,time,toNode), ln);
	}
	
	@Override
	public Path getPath(Supernode supernode, Node toNode, double time) {
		PathTimeKey d = new PathTimeKey(supernode,time,toNode);
		Path p = container.floorEntry(d).getValue();
		return p;
	}  
	
	@Override
	public boolean empty() {
		if (container.size() == 0)
			return true;
		else 
			return false;
	}
}
