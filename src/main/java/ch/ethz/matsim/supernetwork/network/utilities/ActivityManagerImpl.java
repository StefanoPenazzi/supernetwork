/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.utilities;

import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.network.NetworkUtils;
import org.matsim.facilities.ActivityFacilities;
import org.matsim.facilities.FacilitiesUtils;
import org.matsim.facilities.Facility;

import com.google.inject.Inject;

/**
 * @author stefanopenazzi
 *
 */
public class ActivityManagerImpl implements ActivityManager {

	private final Network network;
	private final ActivityFacilities facilities;
	
	@Inject
	public ActivityManagerImpl(Network network,ActivityFacilities facilities) {
		this.network = network;
		this.facilities = facilities;
	}
	
	@Override
	public Node ActivityFromNode(Activity activity) {
		Facility facility = FacilitiesUtils.toFacility(activity, facilities);
		Link linkActivity = this.network.getLinks().get(facility.getLinkId());
		if ( linkActivity==null ) {
			Gbl.assertNotNull( facility.getCoord() ) ;
			linkActivity = NetworkUtils.getNearestLink(network, facility.getCoord());
		}
		Gbl.assertNotNull(linkActivity);
		Node node = linkActivity.getFromNode();
		return node;
	}
	
	@Override
	public Node ActivityToNode(Activity activity) {
		Facility facility = FacilitiesUtils.toFacility(activity, facilities);
		Link linkActivity = this.network.getLinks().get(facility.getLinkId());
		if ( linkActivity==null ) {
			Gbl.assertNotNull( facility.getCoord() ) ;
			linkActivity = NetworkUtils.getNearestLink(network, facility.getCoord());
		}
		Gbl.assertNotNull(linkActivity);
		Node node = linkActivity.getToNode();
		return node;
	}
}
