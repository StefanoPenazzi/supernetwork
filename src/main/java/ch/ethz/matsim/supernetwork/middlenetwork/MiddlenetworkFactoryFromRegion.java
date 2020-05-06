/**
 * 
 */

package ch.ethz.matsim.supernetwork.middlenetwork;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;
import org.matsim.facilities.Facility;

import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.centroid.CALRegion;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.Element;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.middlelink.MiddlelinkImpl;
import ch.ethz.matsim.supernetwork.simulation_data.traffic_data.container.TrafficDataContainer;
import ch.ethz.matsim.supernetwork.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.supernode.SupernodeImpl;
import ch.ethz.matsim.supernetwork.utilities.graph.LinkWeight;


/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkFactoryFromRegion implements MiddlenetworkFactory {

    private final TrafficDataContainer trafficDataContainer;
    private final Network network;
    private final ActivityFacilities facilities;
	
	@Inject
	public MiddlenetworkFactoryFromRegion(TrafficDataContainer trafficDataContainer, Network network,ActivityFacilities facilities) {
		this.trafficDataContainer = trafficDataContainer;
		this.network = network;
		this.facilities = facilities;
	}
	
	@Override
	public Middlenetwork create(Cluster cluster, Subnetwork subnetwork) {
		
		MiddlenetworkImpl mni = new MiddlenetworkImpl(this.trafficDataContainer,cluster,subnetwork);
		
		CALRegion r = (CALRegion)cluster;
		
		SupernodeImpl sn = new SupernodeImpl();
		sn.setCoord(r.getCentroid());
		
		List<Middlelink> middleLinks = new ArrayList<Middlelink>();
		for(Node n: r.getRegionNodes()) {
			MiddlelinkImpl ml = new MiddlelinkImpl(sn,n, LinkWeight.lineDistance(sn,n));
			middleLinks.add(ml);
		}
		
		sn.setOutMiddleLinks(middleLinks);
		
		mni.setMiddleLinks(middleLinks);
		mni.setSuperNode(sn);
		
		//set toNodes
		List<Node> toNodes = new ArrayList<Node>();
		for(ElementActivity e : r.getComponents()) {
			if(e.getNextActivity() != null) {
				Facility f = FacilitiesUtils.toFacility( e.getNextActivity(), facilities);
				Link toLink = this.network.getLinks().get(f.getLinkId());//fromFacility.getLinkId());
				Node n = toLink.getFromNode();
				toNodes.add(n);
			}
		}
		mni.setToNodes(toNodes);
		
		return mni;
	}
}
