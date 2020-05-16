/**
 * 
 */
package ch.ethz.matsim.supernetwork.mobsim;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.mobsim.qsim.qnetsimengine.DefaultTurnAcceptanceLogic;
import org.matsim.core.mobsim.qsim.qnetsimengine.QLaneI;
import org.matsim.core.mobsim.qsim.qnetsimengine.QLinkI;
import org.matsim.core.mobsim.qsim.qnetsimengine.QNetwork;
import org.matsim.core.mobsim.qsim.qnetsimengine.QVehicle;
import org.matsim.core.mobsim.qsim.qnetsimengine.TurnAcceptanceLogic;
import org.matsim.core.mobsim.qsim.qnetsimengine.TurnAcceptanceLogic.AcceptTurn;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkTurnAcceptanceLogic implements TurnAcceptanceLogic  {

private static final Logger log = Logger.getLogger( DefaultTurnAcceptanceLogic.class) ;
	
	@Override
	/** We need qNetwork to get the next QLink, because the link lookup may lead to a NullPointer otherwise */
	public AcceptTurn isAcceptingTurn(Link currentLink, QLaneI currentLane, Id<Link> nextLinkId, QVehicle veh, QNetwork qNetwork, double now){
		if (nextLinkId == null) {
			log.error( "Agent has no or wrong route! agentId=" + veh.getDriver().getId()
					+ " currentLink=" + currentLink.getId().toString()
					+ ". The agent is removed from the simulation.");
			return AcceptTurn.ABORT;
		}
		QLinkI nextQLink = qNetwork.getNetsimLinks().get(nextLinkId);
		
		if (nextQLink == null){
			log.warn("The link id " + nextLinkId + " is not available in the simulation network, but vehicle " + veh.getId() + 
					" plans to travel on that link from link " + veh.getCurrentLink().getId());
			return AcceptTurn.ABORT ;
		}
		
		//Supernetwork uses supernode as root for a rereouting. The first node of the route could not be the same node in which the vehicle stops
		//in the previous trip. TThis is why the following is commented
//		if (currentLink.getToNode() != nextQLink.getLink().getFromNode()) {
//			log.warn("Cannot move vehicle " + veh.getId() + " from link " + currentLink.getId() + " to link " + nextQLink.getLink().getId());
//			return AcceptTurn.ABORT ;
//		}
		
		
//		if ( !nextQLink.getLink().getAllowedModes().contains( veh.getDriver().getMode() ) ) {
//			final String message = "The link with id " + nextLinkId + " does not allow the current mode, which is " + veh.getDriver().getMode();
//			throw new RuntimeException( message ) ;
////			log.warn(message );
////			return acceptTurn.ABORT ;
//			// yyyy is rather nonsensical to get the mode from the driver, not from the vehicle.  However, this seems to be 
//			// how it currently works: network links are defined for modes, not for vehicle types.  kai, may'16
//		}
		// currently does not work, see MATSIM-533 
		
		/* note: it cannot happen, that currentLane does not lead to nextLink (e.g. due to turn restrictions) because this is checked before: 
		 * a vehicle only enters a lane when that lane leads to the next link. see QLinkLanesImpl.moveBufferToNextLane() and .chooseNextLane()
		 * tthunig, oct'17 */
		
		return AcceptTurn.GO ;
	}

}
