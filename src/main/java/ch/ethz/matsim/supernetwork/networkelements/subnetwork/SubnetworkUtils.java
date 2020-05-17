/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkelements.subnetwork;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;

/**
 * @author stefanopenazzi
 *
 */
public final class SubnetworkUtils {
	
	private static int idSubNetworkCounter = 0;
	
	public static Subnetwork circularSubnetwork(Network father,Coord centre, double radius) {
		List<Node> nodes = new ArrayList();
		List<Link> links = new ArrayList();
		for(Node n: father.getNodes().values()) {
			double dist = Math.sqrt(Math.pow(n.getCoord().getX()-centre.getX(), 2)+Math.pow(n.getCoord().getY()-centre.getY(), 2));
			if(dist < radius) {
				nodes.add(n);
				for(Link l:n.getOutLinks().values()) {
					links.add(l);
				}
			}
		}
		SubnetworkDefaultImpl res = new SubnetworkDefaultImpl(idSubNetworkCounter,nodes,links);
		idSubNetworkCounter++;
		return res;
	}
	
}