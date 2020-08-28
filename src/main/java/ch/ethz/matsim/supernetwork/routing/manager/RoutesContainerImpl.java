/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.manager;

import java.util.TreeMap;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.routing.manager.PathTimeKey;

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
	public void add(Node fromNode, Node toNode, double time,Path ln) {
		container.put(new PathTimeKey(fromNode,time,toNode), ln);
	}
	
	@Override
	public Path getPath(Node fromNode, Node toNode, double time) {
		PathTimeKey d = new PathTimeKey(fromNode,time,toNode);
		Path p = null;
		try {
		    p = container.ceilingEntry(d).getValue();
		}
		catch(NullPointerException e) {
			System.out.println("***************** " + fromNode.getId().toString() + " -- "+ toNode.getId().toString() + " -- "+ time);
		}
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
