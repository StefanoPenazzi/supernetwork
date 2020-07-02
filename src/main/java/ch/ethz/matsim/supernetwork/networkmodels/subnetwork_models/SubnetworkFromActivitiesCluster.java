/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkmodels.subnetwork_models;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.SubnetworkDefaultImpl;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.SubnetworkFactory;
import ch.ethz.matsim.supernetwork.network.networkelements.subnetwork.SubnetworkUtils;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkFromActivitiesCluster {
	
	public static Subnetwork fromActivitiesLocations(Network father ,Cluster<ElementActivity> cluster,double cut) {
		SubnetworkDefaultImpl sn = null;
		((ClusterActivitiesLocation)cluster).sortActivitiesByCentroidDistNextAct();
		double radius = 0;
		double xCentroid = cluster.getCentroid().getX();
		double yCentroid = cluster.getCentroid().getY();
		
		for(int i =0; i< cluster.getComponents().size()*cut; ++i) {
			ElementActivity ea = cluster.getComponents().get(i);
//			if(ea.getNextActivity() != null) {
//				double dist = Math.pow(xCentroid - ea.getNextActivity().getCoord().getX(), 2)+
//						Math.pow(yCentroid - ea.getNextActivity().getCoord().getY(), 2);
//				if(radius < dist) {
//					radius = dist;
//				}
//			}
		}
		if(radius >0) {
			sn = (SubnetworkDefaultImpl)SubnetworkUtils.circularSubnetwork(father,cluster.getCentroid(),Math.sqrt(radius));
		}
		return sn;
	}
	
	public double networkByActivitiesRadius(Cluster<ElementActivity> cluster) {
		double radius =0;
		for(ElementActivity ea: cluster.getComponents()) {
//			if(ea.getNextActivity() != null) {
//				double dist = Math.pow(cluster.getCentroid().getX()- ea.getNextActivity().getCoord().getX(), 2)+
//						Math.pow(cluster.getCentroid().getY()- ea.getNextActivity().getCoord().getY(), 2);
//				if(radius<dist) {
//					radius = dist;
//				}
//			}
		}
		return Math.sqrt(radius);
	}
	
	public static class Factory implements SubnetworkFactory{

		Scenario scenario;
		
		@Inject 
		public Factory(Scenario scenario) {
			this.scenario = scenario;
		}
		
		public Subnetwork generateSubnetworkByCluster(Cluster<ElementActivity> cluster) {
			return fromActivitiesLocations(scenario.getNetwork(),cluster,0.9);
		}

	}

}