/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.models.centroid;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.utils.objectattributes.AttributeConverter;

import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.CALNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.models.ClusteringAlgorithm;





/**
 * @author stefanopenazzi
 *
 */
public class ClusteringNetworkRegionAlgorithm implements ClusteringAlgorithm {

	private List<CALNetworkRegionImpl> regions = new ArrayList();
	
	public List<CALNetworkRegionImpl> getRegions(){
		return regions;
	}
	
	public ClusteringNetworkRegionAlgorithm(Scenario scenario) {
		regions = regionsFinder(wedgesFinder(scenario));
		for(CALNetworkRegionImpl r: regions) {
			r.setAreaAndCentroid();
		}
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
			double angle;
			
			if(deltaX==0) {
				if(deltaY >= 0) { 
					angle = Math.PI/2;
					}
				else{
					angle = Math.PI*1.5;
					}
			}
			else if(deltaY==0) {
				if(deltaX >= 0) { 
					angle = 0;
					}
				else{
					angle = Math.PI;
					}
			}
			else {
				angle = Math.atan(deltaY/deltaX);
				//redo considering deltaX = 0
				if (deltaX > 0 && deltaY <0) {
					angle = angle + 2*Math.PI;
				}
				else if(deltaX < 0 && deltaY >0) {
					angle = angle + Math.PI;
				}
				else if(deltaX < 0 && deltaY <0) {
					angle = angle + Math.PI;
				}
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
		//with the same fromNode to build a wedge. last and first link with same fromNode
		//have to be combined in a wedge 
		Triplet<Node, Node,Double>  firstLink = linksAngle.get(0);
		for(int j = 0 ;j<linksAngle.size();j++) {
			
			if(!firstLink.getValue0().equals(linksAngle.get(j).getValue0())) {
				firstLink = linksAngle.get(j);
			}
			
			if(j == linksAngle.size()-1) {
				wedges.add(new Triplet<Node, Node,Node>(firstLink.getValue1(),linksAngle.get(j).getValue0(),linksAngle.get(j).getValue1()));
				continue;
			}
			
			if(linksAngle.get(j).getValue0().getId().equals(linksAngle.get(j+1).getValue0().getId())) {
				wedges.add(new Triplet<Node, Node,Node>(linksAngle.get(j+1).getValue1(),linksAngle.get(j).getValue0(),linksAngle.get(j).getValue1()));
			}
			else {
				wedges.add(new Triplet<Node, Node,Node>(firstLink.getValue1(),linksAngle.get(j).getValue0(),linksAngle.get(j).getValue1()));
			}
		}
		
		return wedges;
	}

	/**
	 * This is the second phase of the algorithm. From wedges to region
	 * The output is a list of list of nodes where the nodes are sorted such that they
	 * represent a polygon.  
	 */
	private List<CALNetworkRegionImpl> regionsFinder(List<Triplet<Node, Node, Node>> wedges){
		List<CALNetworkRegionImpl> regions = new ArrayList();
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
		
		//step 2)
		//find the regions
		for(int j = 0;j < wedgesUsed.size();++j) {
			//Start a new region
			if(!wedgesUsed.get(j)) {
				CALNetworkRegionImpl reg = new CALNetworkRegionImpl(0);
				wedgesUsed.set(j,true);
				List<Node> newRegionNodes = new ArrayList();
				newRegionNodes.add(wedges.get(j).getValue0());
				newRegionNodes.add(wedges.get(j).getValue1());
				newRegionNodes.add(wedges.get(j).getValue2());
				//find the next wedge to insert in the region
				while(true) {
					int indexNewWedge = wedgeBinarySearch(wedges, 0, wedges.size()-1  ,new Pair<Node,Node>(newRegionNodes.get(newRegionNodes.size()-2),newRegionNodes.get(newRegionNodes.size()-1)));
					//in this case the area can not be found. the graph in fact is not a perfect planar graph
					//all these not closed area need to be managed in different a way
					if(indexNewWedge == -1) break;
					wedgesUsed.set(indexNewWedge,true);
					if(wedges.get(indexNewWedge).getValue1() == newRegionNodes.get(0) && wedges.get(indexNewWedge).getValue2() == newRegionNodes.get(1))
					{
						//last value is equal to the first this is useful for the area computation
						//newRegionNodes.add(wedges.get(indexNewWedge).getValue2());
						reg.setNodes(newRegionNodes);
						regions.add(reg);
					    break;
					}
					newRegionNodes.add(wedges.get(indexNewWedge).getValue2());
				}
			}
		}
		
		return regions;
	}
	
	/**
	 * Build the network needed by the algorithm
	 */
	private Network directedCompleteNetwork(Scenario scenario) {
		Map<Class<?>, AttributeConverter<?>> attributeConverters = Collections.emptyMap();
		Network net = NetworkUtils.createNetwork(scenario.getConfig());
		List<Pair<Node,Node>> newLinksFromToNodes = new ArrayList();
		List<Link> deadEndLinks = new ArrayList();
		List<Link> twinLinks = new ArrayList();
		List<Node> deadEndNodes = new ArrayList();
		List<Link> ptLinks = new ArrayList();
		List<Node> ptNodes = new ArrayList();
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
		
		//Pt network is not considered
		for (Link l : net.getLinks().values()) {
			if(l.getId().toString().contains("pt")) {
				ptLinks.add(l);
			}
		}
		for (Link l: ptLinks) {
			net.removeLink(l.getId());
		}
		for (Node n : net.getNodes().values()) {
			if(n.getId().toString().contains("pt")) {
				ptNodes.add(n);
			}
		}
		for (Node n: ptNodes) {
			net.removeNode(n.getId());
		}
		
		
		//the algorithm requires a plane graph
		//1)elimination of dead ends
		while(!stopDeadEnds) {
			stopDeadEnds = true;
			for (Link l : net.getLinks().values()) {
				if(l.getToNode().getOutLinks().size() == 0) {
					deadEndLinks.add(l);
					deadEndNodes.add(l.getToNode());
					stopDeadEnds = false;
				}
				if(l.getToNode().getOutLinks().size() == 1) {
					if(l.getToNode().getOutLinks().entrySet().iterator().next().getValue().getToNode() == l.getFromNode()) {
						deadEndLinks.add(l);
						deadEndLinks.add(l.getToNode().getOutLinks().entrySet().iterator().next().getValue());
						deadEndNodes.add(l.getToNode());
						stopDeadEnds = false;
					}
				}
			}
			//erase these links from the graph
			for (Link deadEndLink : deadEndLinks) {
				net.removeLink(deadEndLink.getId());
			}
			//erase the nodes from the graph
			for (Node deadEndNode : deadEndNodes) {
				net.removeNode(deadEndNode.getId());
			}
		}
		//2)two nodes cannot have more than 1 link connected them
		for(Node n: net.getNodes().values()){
			List<Node> visitedNode = new ArrayList();
			for(Link l: n.getOutLinks().values()) {
				if(visitedNode.stream().filter(o -> o.equals(l.getToNode())).findFirst().isPresent()) {
					twinLinks.add(l);
				}
				else {
					visitedNode.add(l.getToNode());
				}
			}
		}
		//erase twin links from the graph
		for (Link twinLink : twinLinks) {
			net.removeLink(twinLink.getId());
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
		//NetworkUtils.writeNetwork(net, "/home/stefanopenazzi/git/supernetwork/output/supernetworkNoDeadEnds.xml" ); 
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
	        int compFirstNode = wedges.get(mid).getValue0().getId().toString().compareTo(tailWedge.getValue0().getId().toString());
	        int compSecondNode = wedges.get(mid).getValue1().getId().toString().compareTo(tailWedge.getValue1().getId().toString());
	        if(compFirstNode == 0 && compSecondNode ==0) {
	        	return mid;
	        }
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

	
}
