/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;


import org.matsim.api.core.v01.network.Node;

/**
 * @author stefanopenazzi
 *
 */
public class PathTimeKey implements Comparable<PathTimeKey> {

	private Node fromNode;
	private double time;
	private Node toNode;
    // Constructor  
    public PathTimeKey (Node fromNode, double time,Node toNode)  
    {  
        this.fromNode = fromNode;  
        this.time = time;  
        this.toNode = toNode;
    } 
    public Node getFromNode(){
    	return this.fromNode;
    }
    public double getTime() {
    	return time;
    }
    public Node getToNode() {
    	return this.toNode;
    }
    @Override
	public int compareTo(PathTimeKey  domain) {
    	int compFromNode = this.fromNode.getId().compareTo(domain.getFromNode().getId());
		if(compFromNode > 0) {
			return 1;
		}
		else if (compFromNode < 0) {
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
				if(this.time > domain.getTime()) {
					return 1;
				}
				else if(this.time<domain.getTime()) {
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
		return fromNode.getId().toString() + String.valueOf(time);
	}
}
