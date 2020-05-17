/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer;

import java.util.List;
import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.core.router.util.NodeData;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.PredecessorNode;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms.UpdateAlgorithm;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkRoutesContainerImpl implements SupernetworkRoutesContainer{

	private TreeMap<Domain,Path> container = new TreeMap<>();
	private final ContainerManager containerManager;
	
	@Inject
	public SupernetworkRoutesContainerImpl(ContainerManager containerManager) {
		this.containerManager = containerManager;
	}
	
	@Override
	public void add(Supernode supernode, Node toNode, int time,Path ln) {
		container.put(new Domain(supernode,time,toNode), ln);
	}
	
	@Override
	public Path getPath(Supernode supernode, Node toNode, int time) {
		Domain d = new Domain(supernode,time,toNode);
		Path p = container.floorEntry(d).getValue();
		return p;
	}  
	
	public class Domain  implements Comparable<Domain>
	{  
		private Supernode supernode;
		private int time;
		private Node toNode;
	    // Constructor  
	    public Domain(Supernode supernode, int time,Node toNode)  
	    {  
	        this.supernode = supernode;  
	        this.time = time;  
	        this.toNode = toNode;
	    } 
	    public Supernode getSupernode(){
	    	return this.supernode;
	    }
	    public int getTime() {
	    	return time;
	    }
	    public Node getToNode() {
	    	return this.toNode;
	    }
	    @Override
		public int compareTo(Domain domain) {
	    	int compSupernode = this.supernode.getNode().getId().compareTo(domain.getSupernode().getNode().getId());
			if(compSupernode > 0) {
				return 1;
			}
			else if (compSupernode < 0) {
				return -1;
			}
			else {
				int compToNode = this.toNode.getId().compareTo(domain.getToNode().getId());
				if(compToNode > 0) {
					return 1;
				}
				else if(compToNode < 0) {
					return -1;
				}
				else {
					if(this.time > 0) {
						return 1;
					}
					else if(this.time<0) {
						return -1;
					}
					else {
						return 0;
					}
				}
			}
		}
		@Override
		public String toString(){
			return supernode.getNode().getId().toString() + String.valueOf(time);
		}
	}

	@Override
	public void update() {
		
		
	}

	
}
