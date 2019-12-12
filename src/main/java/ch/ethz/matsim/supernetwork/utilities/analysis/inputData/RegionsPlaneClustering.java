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
		List<List<Node>> regions = regionsFinder(wedgesFinder(scenario));
	}

	/**
	 * This is the first phase of the algorithm. Before to find the region is
	 * necessary finding the wedges
	 */
	private List<Triplet<Node, Node, Node>> wedgesFinder(Scenario scenario) {
		Network net = directedCompleteNetwork(scenario);
		List<Triplet<Node, Node,Double>> linksAngle = new ArrayList();
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
			linksAngle.add(nna);
		} 
		
		//step2
		//sort the list into ascending order using nodefrom and the angle as primary and secondary key
		Collections.sort(linksAngle, new Comparator<Triplet<Node, Node,Double>>(){
			@Override
			  public int compare(Triplet<Node, Node,Double> u1, Triplet<Node, Node,Double> u2) {
				int c;
			    c = u1.getValue0().getId().toString().compareTo(u2.getValue0().getId().toString());
			    if(c == 0)
			    	c = u1.getValue2().compareTo(u2.getValue2());
			    return c;
			  }
		});
		
		//step3
		//scan the groups in linkAngles, combine each pair of consecutive entries
		//with the same fromNode to build a 
		//wedge
		for(int j =0 ;j<linksAngle.size()-1;++j) {
			if(linksAngle.get(j).getValue0().getId().equals(linksAngle.get(j+1).getValue0().getId())) {
				wedges.add(new Triplet<Node, Node,Node>(linksAngle.get(j+1).getValue1(),linksAngle.get(j).getValue0(),linksAngle.get(j).getValue1()));
			}
		}
		
		return wedges;
	}

	/**
	 * This is the second phase of the algorithm. From wedges to region
	 * The output is a list of list of nodes where the nodes are sorted such that they
	 * represent a polygon.  
	 */
	private List<List<Node>> regionsFinder(List<Triplet<Node, Node, Node>> wedges){
		List<List<Node>> regions = new ArrayList();
		List<Boolean> wedgesUsed = new ArrayList(Collections.nCopies(wedges.size(),false));
		
		Comparator<Triplet<Node, Node, Node>> wedgeComparator = new Comparator<Triplet<Node, Node, Node>>() {
			@Override
			public int compare(Triplet<Node, Node, Node> u1, Triplet<Node, Node, Node> u2) 
            { 
				int c;
			    c = u1.getValue0().getId().toString().compareTo(u2.getValue0().getId().toString());
			    if(c == 0)
			    	c = u1.getValue1().getId().toString().compareTo(u2.getValue1().getId().toString());
			    return c;
            } 
		};
		
		//step 1)
		//sort the wedge list using the first two nodes as primary and secondary key
		Collections.sort(wedges,wedgeComparator);
		for(Triplet<Node, Node, Node> r: wedges) {
			System.out.println(r.getValue0().getId().toString() + " - " + r.getValue1().getId().toString() + " - " + r.getValue2().getId().toString());
		}
		
		//step 2)
		//find the regions
		for(int j = 0;j < wedgesUsed.size();++j) {
			//Start a new region
			if(!wedgesUsed.get(j)) {
				wedgesUsed.set(j,true);
				
				List<Node> newRegion = new ArrayList();
				newRegion.add(wedges.get(j).getValue0());
				newRegion.add(wedges.get(j).getValue1());
				newRegion.add(wedges.get(j).getValue2());
				
				//find the next wedge to insert in the region
				boolean stopRegion = false;
				while(!stopRegion) {
					int indexNewWedge = wedgeBinarySearch(wedges, 0, wedges.size()-1  ,new Pair<Node,Node>(newRegion.get(newRegion.size()-2),newRegion.get(newRegion.size()-1)));
					
					if(indexNewWedge == -1) {
						//in this case the area can not be found. the graph in fact is not a perfect planar graph
						//all these not closed area need to be managed in different a way
						break;
					}
					
					wedgesUsed.set(indexNewWedge,true);
					if(wedges.get(indexNewWedge).getValue1() == newRegion.get(0) && wedges.get(indexNewWedge).getValue2() == newRegion.get(1))
					{
						newRegion.add(wedges.get(indexNewWedge).getValue1());
						stopRegion = true;
					}
					else {
						//newRegion.add(wedges.get(indexNewWedge).getValue1());
						newRegion.add(wedges.get(indexNewWedge).getValue2());
					}
				}
				regions.add(newRegion);
			}
		}
		
		for(List<Node> r: regions) {
			for(Node n:r) {
				System.out.print(n.getId().toString() + " -- ");
			}
			System.out.println("");
		}
		
		return regions;
	}
	
	/**
	 * The wedges search require edges i->j j->i. If they are not in the network
	 * builded by the config they have to be added.
	 */
	private Network directedCompleteNetwork(Scenario scenario) {
		Map<Class<?>, AttributeConverter<?>> attributeConverters = Collections.emptyMap();
		Network net = NetworkUtils.createNetwork(scenario.getConfig());
		List<Pair<Node,Node>> newLinksFromToNodes = new ArrayList();
		List<Link> deadEndLinks = new ArrayList();
		long idCounter = 1; //redo
		boolean stopDeadEnds = false;

		// read the network from the xml file
		if ((scenario.getConfig().network() != null) && (scenario.getConfig().network().getInputFile() != null)) {
			URL networkUrl = scenario.getConfig().network().getInputFileURL(scenario.getConfig().getContext());
			String inputCRS = scenario.getConfig().network().getInputCRS();

			MatsimNetworkReader reader = new MatsimNetworkReader(inputCRS,
					scenario.getConfig().global().getCoordinateSystem(), net);
			reader.putAttributeConverters(attributeConverters);
			reader.parse(networkUrl);
		}
		//the algorithm requires a plane graph
		//1)elimination of dead ends
		while(!stopDeadEnds) {
			stopDeadEnds = true;
			for (Link l : net.getLinks().values()) {
				if(l.getToNode().getOutLinks().size() == 0) {
					deadEndLinks.add(l);
					stopDeadEnds = false;
				}
				if(l.getToNode().getOutLinks().size() == 1) {
					if(l.getToNode().getOutLinks().entrySet().iterator().next().getValue().getToNode() == l.getFromNode()) {
						deadEndLinks.add(l);
						deadEndLinks.add(l.getToNode().getOutLinks().entrySet().iterator().next().getValue());
						stopDeadEnds = false;
					}
				}
			}
			//erase these links from the graph
			for (Link deadEndLink : deadEndLinks) {
				net.removeLink(deadEndLink.getId());
			}
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
			Id<Link> idl = Id.createLinkId("9999_"+ Long.toString(idCounter));
			Link ll = NetworkUtils.createLink(idl, nn.getValue0(), nn.getValue1(), net, 1, 1, 1, 1);
			net.addLink(ll);
			idCounter--;
		}
		NetworkUtils.writeNetwork(net, "/home/stefanopenazzi/git/supernetwork/output/supernetworkNoDeadWnds.xml" ); 
		return net;
	} 
	
	/**
	 * this method return the index of first wedge Triplet<Node, Node, Node> where the second and the third
	 * node of the first wedge match with the first and second node of the second wedge 
	 */
	private int wedgeBinarySearch(List<Triplet<Node, Node, Node>> wedges,int t , int h,Pair<Node, Node> tailWedge) 
	{ 
	    if (h >= t) { 
	        int mid = t + (h - t) / 2; 
	  
	        // If the element is present at the middle 
	        // itself 
	        if (wedges.get(mid).getValue0().getId().toString() == tailWedge.getValue0().getId().toString()
	        		&& wedges.get(mid).getValue1().getId().toString() == tailWedge.getValue1().getId().toString()) 
	            return mid; 
	  
	        // If element is smaller than mid, then 
	        // it can only be present in left subarray 
	        int compFirstNode = wedges.get(mid).getValue0().getId().toString().compareTo(tailWedge.getValue0().getId().toString());
	        int compSecondNode = wedges.get(mid).getValue1().getId().toString().compareTo(tailWedge.getValue1().getId().toString());
	        if (compFirstNode > 0) 
	            return wedgeBinarySearch(wedges, t, mid - 1, tailWedge); 
	        else if(compFirstNode == 0) {
	        	if(compSecondNode > 0) {
	        		return wedgeBinarySearch(wedges, t, mid - 1, tailWedge);
	        	}
	        	else {
	        		return wedgeBinarySearch(wedges, mid + 1, h, tailWedge);
	        	}
	        }
	        // Else the element can only be present 
	        // in right subarray 
	        return wedgeBinarySearch(wedges, mid + 1, h, tailWedge); 
	    } 
	  
	    // We reach here when element is not 
	    // present in array 
	    return -1; 
	} 

	public HashMap<Integer, List<Activity>> getActivitiesClusteringResult() {
		return clusteringActivitiesResult;
	}

	public HashMap<Integer, Pair<Double, Double>> getClusteringCoordinatesResult() {
		return clusteringCoordinatesResult;
	}

}
