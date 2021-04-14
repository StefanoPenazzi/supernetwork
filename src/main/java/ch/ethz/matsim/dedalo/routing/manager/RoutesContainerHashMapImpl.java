package ch.ethz.matsim.dedalo.routing.manager;

import java.util.HashMap;

import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;

import com.google.inject.Inject;

public class RoutesContainerHashMapImpl implements RoutesContainer{

	private HashMap<PathTimeKey,Path> container = new HashMap<>();
	
	@Inject
	public RoutesContainerHashMapImpl() {
		
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
		    p = container.get(d);
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
