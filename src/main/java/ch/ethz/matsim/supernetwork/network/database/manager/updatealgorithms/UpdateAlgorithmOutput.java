/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms;

import java.util.List;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.networkelements.supernode.Supernode;

/**
 * @author stefanopenazzi
 *
 */
public class UpdateAlgorithmOutput {
	
	private final Supernode supernode;
	private final List<Node> toNodes;
	private final double time;
	
	public UpdateAlgorithmOutput(Supernode supernode, List<Node> toNodes, double time) {
		this.supernode = supernode;
		this.toNodes = toNodes;
		this.time = time;
	}

	public Supernode getSupernode() {
		return this.supernode;
	}
	public List<Node> getToNodes(){
		return this.toNodes;
	}
	public double getTime() {
		return this.time;
	}
}
