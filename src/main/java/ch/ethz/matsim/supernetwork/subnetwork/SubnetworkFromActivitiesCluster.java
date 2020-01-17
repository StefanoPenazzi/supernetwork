/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.supernetwork.clustering.cluster.CALDefaultImpl;
import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;
import ch.ethz.matsim.supernetwork.clustering.element.ElementActivity;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkFromActivitiesCluster {
	
	private static final SubnetworkFactory subnetworkFactory = new SubnetworkFactory();
	
	public static Subnetwork fromActivitiesLocations(Network father ,Cluster<ElementActivity> cluster,double cut) {
		SubnetworkDefaultImpl sn = null;
		((CALDefaultImpl)cluster).sortActivitiesByCentroidDistNextAct();
		double radius = 0;
		double xCentroid = cluster.getCentroid().getX();
		double yCentroid = cluster.getCentroid().getY();
		
		for(int i =0; i< cluster.getComponents().size()*cut; ++i) {
			ElementActivity ea = cluster.getComponents().get(i);
			if(ea.getNextActivity() != null) {
				double dist = Math.pow(xCentroid - ea.getNextActivity().getCoord().getX(), 2)+
						Math.pow(yCentroid - ea.getNextActivity().getCoord().getY(), 2);
				if(radius < dist) {
					radius = dist;
				}
			}
		}
		if(radius >0) {
			sn = (SubnetworkDefaultImpl)subnetworkFactory.circularSubnetwork(father,cluster.getCentroid(),Math.sqrt(radius));
		}
		return sn;
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
