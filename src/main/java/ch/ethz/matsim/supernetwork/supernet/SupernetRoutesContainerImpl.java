/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.List;
import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.core.router.util.NodeData;

import com.google.inject.Inject;

import ch.ethz.matsim.supernetwork.algorithms.router.shortest_path.PredecessorNode;
import ch.ethz.matsim.supernetwork.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetRoutesContainerImpl implements SupernetRoutesContainer{

	private TreeMap<Domain,Path[]> container = new TreeMap<>();
	
	@Override
	public void add(Supernode supernode, int time,Path[] ln) {
		container.put(new Domain(supernode,time), ln);
	}

	@Override
	public Path[] getNodesTree(Supernode supernode, int time) {
		Domain d = new Domain(supernode,time);
		return container.get(d);
	}
	
	class Domain  implements Comparable<Domain>
	{  
		private Supernode supernode;
		private int time;
	    // Constructor  
	    public Domain(Supernode supernode, int time)  
	    {  
	        this.supernode = supernode;  
	        this.time = time;  
	    } 
	    public Supernode getSupernode(){
	    	return this.supernode;
	    }
	    public int getTime() {
	    	return time;
	    }
	    @Override
		public int compareTo(Domain domain) {
	    	int comp = this.supernode.getNode().getId().compareTo(domain.getSupernode().getNode().getId());
			if(comp > 0) {
				return 1;
			}
			else if (comp < 0) {
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
		@Override
		public String toString(){
			return supernode.getNode().getId().toString() + String.valueOf(time);
		}
	}  
}
