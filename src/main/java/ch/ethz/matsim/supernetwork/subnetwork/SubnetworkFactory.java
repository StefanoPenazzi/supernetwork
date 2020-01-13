/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import java.util.List;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;
import ch.ethz.matsim.supernetwork.clustering.element.ElementActivity;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkFactory {
	
	public SubnetworkFactory() {
		
	}
	
	public Network circularNetworkCutByNodes(Network father, List<Node> cutSet) {
		Network net = null;
		
		return net;
	}

	public Network circularNetworkCutByActivities(Network father, List<Activity> cutSet) {
		Network net = null;
		
		
		return net;
	}
	
	public double networkByActivitiesRadius(Cluster<ElementActivity> cluster) {
		double radius =0;
		for(ElementActivity ea: cluster.getComponents()) {
			if(ea.getNextActivity() != null) {
				double dist = Math.pow(cluster.getCentroid().getX()- ea.getNextActivity().getCoord().getX(), 2)+
						Math.pow(cluster.getCentroid().getY()- ea.getNextActivity().getCoord().getY(), 2);
				if(radius<dist) {
					radius = dist;
				}
			}
		}
		return Math.sqrt(radius);
	}
}
