/**
 * 
 */
package ch.ethz.matsim.supernetwork.utilities.analysis.inputData;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.scenario.*;
import org.matsim.utils.objectattributes.AttributeConverter;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

/**
 * this clustering is based on An optimal algorithm for extracting the regions
 * of a plane graph X.Y. Jiang and H.Bunke 1992. All the activities in a region
 * are grouped together. Computational time O(m log m) , memory O(m) m = num.
 * vertices.
 * 
 * @author stefanopenazzi
 *
 */
public class RegionsPlaneClustering implements ActivitiesClusteringAlgo {

	private HashMap<Integer, List<Activity>> clusteringActivitiesResult = new LinkedHashMap<>();
	private HashMap<Integer, Pair<Double, Double>> clusteringCoordinatesResult = new LinkedHashMap<>();

	public RegionsPlaneClustering(Scenario scenario) {
		wedgesFinder(scenario);
	}

	/**
	 * This is the first phase of the algorithm. Before to find the region is
	 * necessary finding the wedges
	 */
	private List<Triplet<Node, Node, Node>> wedgesFinder(Scenario scenario) {
		Network net = directedCompleteNetwork(scenario);
		List<Triplet<Node, Node,Double>> linkAngles = new ArrayList();
		List<Triplet<Node, Node, Node>> wedges = new ArrayList();
		
		//step 1
		//Each link needs the angle with respect to the horizontal line passing through the fromNode
		for (Link l : net.getLinks().values()) {
			double deltaX = l.getToNode().getCoord().getX() - l.getFromNode().getCoord().getX();
			double deltaY = l.getToNode().getCoord().getY() - l.getFromNode().getCoord().getY();
			double angle = Math.atan(deltaY/deltaX);
			//redo considering deltaX = 0
			if (deltaX > 0 && deltaY <0) {
				angle = angle + 2*Math.PI;
			}
			else if(deltaX < 0 && deltaY >0) {
				angle = angle + Math.PI;
			}
			Triplet<Node, Node,Double> nna = new Triplet<Node, Node,Double>(l.getFromNode(),l.getToNode(),angle);
			linkAngles.add(nna);
		} 
		
		//step2
		//sort the list into ascending order using nodefrom and the angle as primary and secondary key
		Collections.sort(linkAngles, new Comparator<Triplet<Node, Node,Double>>(){
			@Override
			  public int compare(Triplet<Node, Node,Double> u1, Triplet<Node, Node,Double> u2) {
				int c;
			    c = u1.getValue0().getId().toString().compareTo(u2.getValue0().getId().toString());
			    if(c == 0)
			    	c = u1.getValue2().compareTo(u2.getValue2());
			    return c;
			  }
		});
		return wedges;
	}

	/**
	 * The wedges search require edges i->j j->i. If they are not in the network
	 * builded by the config they have to be added.
	 */
	private Network directedCompleteNetwork(Scenario scenario) {
		Map<Class<?>, AttributeConverter<?>> attributeConverters = Collections.emptyMap();
		Network net = NetworkUtils.createNetwork(scenario.getConfig());
		List<Pair<Node,Node>> newLinksFromToNodes = new ArrayList();
		long idCounter = -10000; //redo

		// read the network from the xml file
		if ((scenario.getConfig().network() != null) && (scenario.getConfig().network().getInputFile() != null)) {
			URL networkUrl = scenario.getConfig().network().getInputFileURL(scenario.getConfig().getContext());
			String inputCRS = scenario.getConfig().network().getInputCRS();

			MatsimNetworkReader reader = new MatsimNetworkReader(inputCRS,
					scenario.getConfig().global().getCoordinateSystem(), net);
			reader.putAttributeConverters(attributeConverters);
			reader.parse(networkUrl);
		}

		//the algorithm requires that each link has its inverse
		for (Link l : net.getLinks().values()) {
			boolean inverse = false;
			if(l.getToNode().getOutLinks().size() > 0) {
				for (Link lout : l.getToNode().getOutLinks().values()) {
					if (lout.getToNode() == l.getFromNode()) {
						inverse = true;
					}
				}
			}
			if (!inverse) {
				Pair<Node,Node> nn = new Pair<Node,Node>(l.getToNode(), l.getFromNode());
				newLinksFromToNodes.add(nn);
			}
		}
		for(Pair<Node,Node> nn: newLinksFromToNodes) {
			Id<Link> idl = Id.createLinkId(idCounter);
			Link ll = NetworkUtils.createLink(idl, nn.getValue0(), nn.getValue1(), net, 1, 1, 1, 1);
			net.addLink(ll);
			idCounter--;
		}
		return net;
	} 
	

	public HashMap<Integer, List<Activity>> getActivitiesClusteringResult() {
		return clusteringActivitiesResult;
	}

	public HashMap<Integer, Pair<Double, Double>> getClusteringCoordinatesResult() {
		return clusteringCoordinatesResult;
	}

}
