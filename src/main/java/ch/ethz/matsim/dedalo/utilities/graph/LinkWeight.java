/**
 * 
 */
package ch.ethz.matsim.dedalo.utilities.graph;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.dedalo.routing.network.cluster.elements.supernode.Supernode;



/**
 * @author stefanopenazzi
 *
 */
public class LinkWeight {
	
	public static double lineDistance(Node n, Supernode s) {
		double ld = Math.sqrt(Math.pow(n.getCoord().getX() - s.getCoord().getX(), 2)+Math.pow(n.getCoord().getY() - s.getCoord().getY(), 2));
		return ld;
	}
	
	public static double lineDistance(Supernode s,Node n) {
		double ld = Math.sqrt(Math.pow(n.getCoord().getX() - s.getCoord().getX(), 2)+Math.pow(n.getCoord().getY() - s.getCoord().getY(), 2));
		return ld;
	}
	
	public static double lineDistance(Supernode s,Supernode n) {
		double ld = Math.sqrt(Math.pow(n.getCoord().getX() - s.getCoord().getX(), 2)+Math.pow(n.getCoord().getY() - s.getCoord().getY(), 2));
		return ld;
	}

}
