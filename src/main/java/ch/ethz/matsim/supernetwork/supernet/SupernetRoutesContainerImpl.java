/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernet;

import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.router.util.NodeData;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetRoutesContainerImpl implements SupernetRoutesContainer{

	private TreeMap<Domain,NodeData[]> container = new TreeMap<>();
	
	@Override
	public void add(Id<Node> nodeId, int time,NodeData[] nd) {
		container.put(new Domain(nodeId,time), nd);
	}

	@Override
	public NodeData[] get(Id<Node> nodeId, int time) {
		Domain d = new Domain(nodeId,time);
		return container.get(d);
	}
	
	class Domain  implements Comparable<Domain>
	{  
		private Id<Node> nodeId;
		private int time;
	    // Constructor  
	    public Domain(Id<Node> nodeId, int time)  
	    {  
	        this.nodeId = nodeId;  
	        this.time = time;  
	    } 
	    public Id<Node> getNodeId(){
	    	return this.nodeId;
	    }
	    public int getTime() {
	    	return time;
	    }
	    @Override
		public int compareTo(Domain domain) {
	    	int comp = this.nodeId.compareTo(domain.getNodeId());
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
			return nodeId.toString() + String.valueOf(time);
		}
	}  
}
