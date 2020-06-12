/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements;

/**
 * @author stefanopenazzi
 *
 */
public class LinkImpl implements Link {

	private int id;
	private int fromNodeId;
	private int toNodeId;
	
	protected LinkImpl(int id, int fromNodeId, int toNodeId) {
		this.id = id;
		this.fromNodeId = fromNodeId;
		this.toNodeId = toNodeId; 
	}
	
	public int getId() {
		return this.id ;
	};
	public int getFromNodeId() {
		return this.fromNodeId;
	};
	public int getToNodeId() {
		return this.toNodeId;
	}

	@Override
	public void setFromNodeId(int fromNodeId) {
		this.fromNodeId = fromNodeId;
		
	}

	@Override
	public void setToNodeId(int toNodeId) {
		this.toNodeId = toNodeId;
	};
}
