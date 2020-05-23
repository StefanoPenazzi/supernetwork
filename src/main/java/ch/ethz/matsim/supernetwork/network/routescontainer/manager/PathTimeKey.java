/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager;

import org.matsim.api.core.v01.network.Node;
import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class PathTimeKey implements Comparable<PathTimeKey> {

	private Supernode supernode;
	private double time;
	private Node toNode;
    // Constructor  
    public PathTimeKey (Supernode supernode, double time,Node toNode)  
    {  
        this.supernode = supernode;  
        this.time = time;  
        this.toNode = toNode;
    } 
    public Supernode getSupernode(){
    	return this.supernode;
    }
    public double getTime() {
    	return time;
    }
    public Node getToNode() {
    	return this.toNode;
    }
    @Override
	public int compareTo(PathTimeKey  domain) {
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
