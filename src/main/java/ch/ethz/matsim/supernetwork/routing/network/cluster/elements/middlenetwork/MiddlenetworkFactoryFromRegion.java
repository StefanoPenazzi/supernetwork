/**
 * 
 */

package ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.NetworkUtils;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;
import org.matsim.facilities.Facility;
import com.google.inject.Inject;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.CALRegion;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlelink.MiddlelinkImpl;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.subnetwork.Subnetwork;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.supernode.SupernodeImpl;
import ch.ethz.matsim.supernetwork.utilities.graph.LinkWeight;


/**
 * @author stefanopenazzi
 *
 */
public class MiddlenetworkFactoryFromRegion implements MiddlenetworkFactory {

    private final Network network;
    private final ActivityFacilities facilities;
	
	@Inject
	public MiddlenetworkFactoryFromRegion( Network network,ActivityFacilities facilities) {
		this.network = network;
		this.facilities = facilities;
	}
	
	@Override
	public Middlenetwork create(Cluster cluster, Subnetwork subnetwork) {
		
		MiddlenetworkImpl mni = new MiddlenetworkImpl(cluster,subnetwork);
		
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
		//TODO 
		//this has to be consistent with the method used in the RoutingModule. Otherwise the path will not be found in the continer
		List<Node> toNodes = new ArrayList<Node>();
		for(ElementActivity e : r.getComponents()) {
			if(e.getNextFacility() != null) {
				Link toLink;
				if(e.getNextFacility().getLinkId() == null) {
					toLink = NetworkUtils.getNearestLink(network, e.getNextFacility().getCoord());
				}
				else {
					toLink = this.network.getLinks().get(e.getNextFacility().getLinkId());//fromFacility.getLinkId());
				}
				if(toLink == null) {
					System.out.println("No link found");
				}
				Node n = toLink.getFromNode();
				toNodes.add(n);
			}
		}
		mni.setToNodes(toNodes);
		
		return mni;
	}
}
