/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.rerouting;

import java.util.Arrays;
import java.util.List;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.population.routes.RouteUtils;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import org.matsim.facilities.Facility;
import com.google.inject.Inject;

/**
 * @author stefanopenazzi
 *
 */
public class ClustersReRouteModelImpl implements ClustersReRouteModel {

	private final Network network;
	private final ch.ethz.matsim.supernetwork.network.Network supernet;
	private final PopulationFactory populationFactory;
	
	@Inject
	public ClustersReRouteModelImpl(Network network, ch.ethz.matsim.supernetwork.network.Network supernet,final PopulationFactory populationFactory) {
		Gbl.assertNotNull(network);
		this.network = network;
		this.supernet = supernet;
		this.populationFactory = populationFactory;
	}

	@Override
	public List<? extends PlanElement> calcRoute(Activity fromActivity, Facility toFacility, double departureTime,
			Person person) {
		
		Leg newLeg = this.populationFactory.createLeg( "car" );
		Gbl.assertNotNull(toFacility);
		Link toLink = this.network.getLinks().get(toFacility.getLinkId());
		if ( toLink==null ) {
			Gbl.assertNotNull( toFacility.getCoord() ) ;
			toLink = NetworkUtils.getNearestLink(network, toFacility.getCoord());
		}
		Gbl.assertNotNull(toLink);
		
		Node endNode = toLink.getFromNode();
		Path path = this.supernet.getPath(fromActivity, endNode, (int)departureTime,"car");
		
//		String s = "";
//		for(Node n: path.nodes) {
//			s+= n.getCoord().getX() + ","+n.getCoord().getY()+";";
//		
//		}
//		System.out.println(s);
		
		if (path == null || path.nodes.size()<2) {
			//throw new RuntimeException("No route found from activity " + fromActivity.getLinkId() + " to node " + endNode.getId() + " by mode car.");
			return null;
		}
		Link fromLink = path.links.get(0);
		NetworkRoute route = this.populationFactory.getRouteFactories().createRoute(NetworkRoute.class, fromLink.getId(), toLink.getId());
		route.setLinkIds(fromLink.getId(), NetworkUtils.getLinkIds(path.links), toLink.getId());
		route.setTravelTime(path.travelTime);
		route.setTravelCost(path.travelCost);
		route.setDistance(RouteUtils.calcDistance(route, 1.0, 1.0, this.network));
		newLeg.setRoute(route);
		newLeg.setTravelTime(path.travelTime);
		newLeg.setDepartureTime(departureTime);
		
		return Arrays.asList( newLeg );
	}

}
