/**
 * 
 */
package ch.ethz.matsim.supernetwork.middlenetwork;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.network.Node;

import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.CALRegion;
import ch.ethz.matsim.supernetwork.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.middlelink.MiddlelinkImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.supernode.SupernodeImpl;


/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkFactoryFromRegion implements MiddlenetworkFactory {

    private final TrafficDataContainer trafficDataContainer;
	
	@Inject
	public MiddlenetworkFactoryFromRegion(TrafficDataContainer trafficDataContainer) {
		this.trafficDataContainer = trafficDataContainer;
	}
	
	@Override
	public Middlenetwork create(Cluster cluster, Subnetwork subnetwork) {
		
		MiddlenetworkImpl mni = new MiddlenetworkImpl(this.trafficDataContainer,cluster,subnetwork);
		
		CALRegion r = (CALRegion)cluster;
		
		SupernodeImpl sn = new SupernodeImpl();
		sn.setCoord(r.getCentroid());
		
		List<Middlelink> middleLinks = new ArrayList<Middlelink>();
		for(Node n: r.getRegionNodes()) {
			//TODO insert distance between supernode and the subnetwork node
			MiddlelinkImpl ml = new MiddlelinkImpl(mni.getSuperNode(),n,0);
			middleLinks.add(ml);
		}
		
		sn.setOutMiddleLinks(middleLinks);
		
		mni.setMiddleLinks(middleLinks);
		mni.setSuperNode(sn);
		
		return mni;
	}
}
