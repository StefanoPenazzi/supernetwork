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
import java.util.Stack;
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

	private List<Region> regions = new ArrayList();
	private HashMap<Integer, List<Activity>> clusteringActivitiesResult = new LinkedHashMap<>();
	private HashMap<Integer, Pair<Double, Double>> clusteringCoordinatesResult = new LinkedHashMap<>();
	

	public RegionsPlaneClustering(Scenario scenario) {
		regions = regionsFinder(wedgesFinder(scenario));
		regionsCentroid(regions);
		activitiesRegionsMatch(regions,scenario);
		
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
			else if(deltaX < 0 && deltaY <0) {
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
		//with the same fromNode to build a wedge. last and first link with same fromNode
		//have to be combined in a wedge 
		Triplet<Node, Node,Double>  firstLink = linksAngle.get(0);
		for(int j =0 ;j<linksAngle.size()-1;++j) {
			
			if(!firstLink.getValue0().equals(linksAngle.get(j).getValue0())) {
				firstLink = linksAngle.get(j);
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
	private List<Region> regionsFinder(List<Triplet<Node, Node, Node>> wedges){
		List<Region> regions = new ArrayList();
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
				Region reg = new Region();
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
						newRegionNodes.add(wedges.get(indexNewWedge).getValue2());
					    break;
					}
					newRegionNodes.add(wedges.get(indexNewWedge).getValue2());
				}
				reg.setNodes(newRegionNodes);
				regions.add(reg);
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

	/**
	 * this method search in which region an activity is located 
	 */
	private void activitiesRegionsMatch(List<Region> regions, Scenario scenario){
		//order the region by the centroid coordinates x first key y second key

		TreeRegions tr = new TreeRegions(regions);
		tr.print();
	}
	
	private void regionsCentroid(List<Region> regions){
		
		for(Region r: regions) {
			double x = 0;
			double y = 0;
			double area = 0;
			for(int j =0;j<r.getNodes().size()-2;++j) {
				double currX = r.getNodeCoord(j).getValue0();//ln.get(j).getCoord().getX();
				double currXPlusOne = r.getNodeCoord(j+1).getValue0();//ln.get(j+1).getCoord().getX();
				double currY = r.getNodeCoord(j).getValue1();
				double currYPlusOne = r.getNodeCoord(j+1).getValue1();
				x += (currX + currXPlusOne)*(currX* currYPlusOne - currXPlusOne*currY);
				y += (currY + currYPlusOne)*(currX* currYPlusOne - currXPlusOne*currY);
				area += currX*currYPlusOne - currXPlusOne*currY;
			}
			area = Math.abs(area/2);
			x = (1/(6*area))*x;
			y = (1/(6*area))*y;
			r.setArea(area);
			r.setCentroidCoord(new Pair<Double,Double>(x,y));
		}

	}
	
	
	public HashMap<Integer, List<Activity>> getActivitiesClusteringResult() {
		return clusteringActivitiesResult;
	}

	public HashMap<Integer, Pair<Double, Double>> getClusteringCoordinatesResult() {
		return clusteringCoordinatesResult;
	}

	
	private class Region{
		private List<Node> nodes = new ArrayList();
		private double area = 0;
		private int id = -1;
		private Pair<Double,Double> centroidCoord = new Pair<Double,Double>(-1.0,-1.0);
		
		public List<Node> getNodes(){
			return nodes;
		}
		public double getArea() {
			return area;
		}
		public int getId() {
			return id;
		}
		public Pair<Double,Double> getCentroidCoord(){
			return centroidCoord;
		} 
		public void setNodes(List<Node> nodes){
			 this.nodes = nodes;
		}
		public void setArea( double area) {
			 this.area = area;
		}
		public void setId(int id) {
			 this.id = id;
		}
		public void setCentroidCoord(Pair<Double,Double> centroidCoord){
			 this.centroidCoord = centroidCoord;
		} 
		public void addNodes(Node node){
			 this.nodes.add(node);
		}
		public Pair<Double,Double> getNodeCoord(int i){
			return new Pair<Double,Double>(nodes.get(i).getCoord().getX(),nodes.get(i).getCoord().getY());
		}
	}

	//k-d tree implementation for regions. this is a structure used to quickly find the region centroid to an input point
	//[] {}
    private class TreeRegionsNode{
		 private Region reg = null;
		 private TreeRegionsNode child = null;
		 private TreeRegionsNode left = null;
		 private TreeRegionsNode right = null;
		 private boolean lr = false;
		 private boolean checked;
		 
		 public TreeRegionsNode(Region reg,boolean lr,TreeRegionsNode child){
			 this.reg= reg;
			 this.lr = lr;
			 this.child = child;
		 }
		 
		 public Region getReg() {
			 return this.reg;
		 }
		 public TreeRegionsNode getChild() {
			 return this.child;
		 }
		 public TreeRegionsNode getLeft() {
			 return this.left;
		 }
		 public TreeRegionsNode getRight() {
			 return this.right;
		 }
		 public boolean getLr() {
			 return this.lr;
		 }
		 public void setChild(TreeRegionsNode child){
			 this.child = child;
		 }
		 public void setLeft(TreeRegionsNode left){
			 this.left = left;
		 }
		 public void setRight(TreeRegionsNode right){
			 this.right = right;
		 }
		 
		 public boolean getChecked() {
			 return this.checked;
		 }
		 
		 public void setChecked(boolean checked) {
			 this.checked = checked;
		 }
		 
		 public double squareDist(Activity act) {
			 return Math.sqrt(this.reg.getCentroidCoord().getValue0() - act.getCoord().getX()) + Math.sqrt(this.reg.getCentroidCoord().getValue1() - act.getCoord().getY());
		 }
		 
		 public TreeRegionsNode findParent(Activity act) {
			 TreeRegionsNode parent = null;
			 TreeRegionsNode next = this;
			 boolean split;
			 while (next != null) {
				 split = next.lr;
				 parent = next;
				 if(lr) {
					 if(next.getReg().getCentroidCoord().getValue0()<=act.getCoord().getX()){
						 next = next.getRight();
					 }
					 else{
						 next = next.getLeft();
					 }
				 }
				 else {
					 if(next.getReg().getCentroidCoord().getValue1()<=act.getCoord().getY()){
						 next = next.getRight();
					 }
					 else{
						 next = next.getLeft();
					 }
				 }
			 }
			 return parent;
		 }
		 
		 public void print(){
			 String sRight = this.getRight() == null ? "null" : "x = "+this.getRight().getReg().getCentroidCoord().getValue0().toString()+ " y = "+this.getRight().getReg().getCentroidCoord().getValue1().toString();
			 String sLeft = this.getLeft() == null ? "null" : "x = " + this.getLeft().getReg().getCentroidCoord().getValue0().toString()+ " y = "+this.getLeft().getReg().getCentroidCoord().getValue1().toString();
			 System.out.println("--> x = "+reg.getCentroidCoord().getValue0()+" y = "+ reg.getCentroidCoord().getValue1()+" >(right) " + sRight
					 +" <(left) "+ sLeft + " <>(left or right) : "+ lr);
		 }
	}	 
	
   private class TreeRegions{
	   
	   private TreeRegionsNode root = null;
	   List<Region> regions;
	   TreeRegionsNode CheckedNodes[];
	   TreeRegionsNode nearestNeighbour;
	   int checkedNodes;
	   int nBoundary;
	   double xMin, xMax;
	   double yMin, yMax;
	   double dMin;
	   boolean xMaxBoundary, xMinBoundary;
	   boolean yMaxBoundary, yMinBoundary;
	   
	   
	   public TreeRegions(List<Region> regions){
		   this.regions = regions;
		   for(Region r:regions){
			   root = insert(root,r);
			   //this.print();
		   }
	   }
	   
	   public TreeRegionsNode getRoot(){
		   return this.root;
	   }
	   
	   public TreeRegionsNode newTreeNode(Region reg,boolean lr,TreeRegionsNode child){
			return new TreeRegionsNode(reg,lr,child);
	   }
	   public TreeRegionsNode insertRec(TreeRegionsNode root, Region newReg, boolean lr,TreeRegionsNode child)
		 {
			 if(root == null){
				 return newTreeNode(newReg,lr,null);
			 }
			 else{
				 if(lr) // x
				 { 
					 if(root.getReg().getCentroidCoord().getValue0()<=newReg.getCentroidCoord().getValue0()){
						 root.setRight(this.insertRec(root.getRight(), newReg, !lr,root));
					 }
					 else{
						 root.setLeft(this.insertRec(root.getLeft(), newReg, !lr,root));
					 }
				 }
				 else
				 {
					 if(root.getReg().getCentroidCoord().getValue1()<=newReg.getCentroidCoord().getValue1()){
						 root.setRight(this.insertRec(root.getRight(), newReg, !lr,root));
					 }
					 else{
						 root.setLeft(this.insertRec(root.getLeft(), newReg, !lr,root));
					 }
				 }
				 return root;
			 }
			 
		 }
	   
	   public TreeRegionsNode insert(TreeRegionsNode node, Region newReg){
		   return insertRec(node, newReg, false,null);
	   }
	   
	   private TreeRegionsNode nearestNeighbourSearch(Activity act) {
		   
		   if (root == null)
	            return null;
		   
		   checkedNodes = 0;
		   TreeRegionsNode parent = root.findParent(act);
		   nearestNeighbour = parent;
		   dMin = parent.squareDist(act);
		   
		   if(parent.getReg().getCentroidCoord().getValue0() == act.getCoord().getX() &&
				   parent.getReg().getCentroidCoord().getValue1() == act.getCoord().getY()) {
			   return parent;
		   }
		   searchParent(parent, act);
		   //clean for the next query
		   uncheck();
		   return nearestNeighbour; 
	   }
	   
	   public void setBoundingCube(TreeRegionsNode node, Activity act)
	    {
	        if (node == null)
	            return;
	        double d = 0;
	        double dx;
	        double dy;
	        
	        //xbound
            dx = node.getReg().getCentroidCoord().getValue0() - act.getCoord().getX();
            if (dx > 0)
            {
                dx *= dx;
                if (!xMaxBoundary)
                {
                    if (dx > xMax)
                        xMax = dx;
                    if (xMax > dMin)
                    {
                        xMaxBoundary = true;
                        nBoundary++;
                    }
                }
            } else
            {
                dx *= dx;
                if (!xMinBoundary)
                {
                    if (dx > xMin)
                        xMin = dx;
                    if (xMin > dMin)
                    {
                        xMinBoundary = true;
                        nBoundary++;
                    }
                }
            }
            //ybound
            dy = node.getReg().getCentroidCoord().getValue1() - act.getCoord().getY();
            if (dy > 0)
            {
                dy *= dy;
                if (!yMaxBoundary)
                {
                    if (dy > yMax)
                        yMax = dy;
                    if (yMax > dMin)
                    {
                        yMaxBoundary = true;
                        nBoundary++;
                    }
                }
            } else
            {
                dy *= dy;
                if (!yMinBoundary)
                {
                    if (dy > yMin)
                        yMin = dy;
                    if (yMin > dMin)
                    {
                        yMinBoundary = true;
                        nBoundary++;
                    }
                }
            }
            
            d =  dx + dy;
            if (d > dMin)
                return;
	        
	        if (d < dMin)
	        {
	            dMin = d;
	            nearestNeighbour = node;
	        }
	    }
	   
	   // this is method check the subtree considering as the root the node in the param
	   public void checkSubtree(TreeRegionsNode node, Activity act)
	    {
	        if ((node == null) || node.checked)
	            return;
	 
	        CheckedNodes[checkedNodes++] = node;
	        node.checked = true;
	        setBoundingCube(node, act);
	 
	        boolean lr = node.getLr();
	        double dist;
	        if (lr) {
	        	dist = node.getReg().getCentroidCoord().getValue0() - act.getCoord().getX();
	        }
	        else {
	        	dist = node.getReg().getCentroidCoord().getValue1() - act.getCoord().getY();
	        }
	        
	        if (dist * dist > dMin)
	        {
	        	if(lr) {
	        		if (node.getReg().getCentroidCoord().getValue0() > act.getCoord().getX())
		                checkSubtree(node.getLeft(), act);
		            else
		                checkSubtree(node.getRight(), act);
	        	}
	        	else {
	        		if (node.getReg().getCentroidCoord().getValue1() > act.getCoord().getY())
		                checkSubtree(node.getLeft(), act);
		            else
		                checkSubtree(node.getRight(), act);
	        	}
	            
	        } else
	        {
	            checkSubtree(node.getLeft(), act);
	            checkSubtree(node.getRight(), act);
	        }
	    }
	   
	   public TreeRegionsNode searchParent(TreeRegionsNode parent, Activity act)
	    {
		    xMin = xMax = yMin = yMax =0;
		    xMaxBoundary = xMinBoundary = yMaxBoundary = yMinBoundary = false;
	        nBoundary = 0;
	 
	        TreeRegionsNode searchRoot = parent;
	        while (parent != null && (nBoundary != 4))
	        {
	            checkSubtree(parent, act);
	            searchRoot = parent;
	            //backtracking
	            parent = parent.getChild();
	        }
	 
	        return searchRoot;
	    }
	 
	    public void uncheck()
	    {
	        for (int n = 0; n < checkedNodes; n++)
	            CheckedNodes[n].checked = false;
	    }
	   
	   public void print(){
		   Stack<TreeRegionsNode> waitingNode = new Stack();
		   waitingNode.push(root);
		   while(!waitingNode.isEmpty()){
			   TreeRegionsNode ext = waitingNode.pop();
			   ext.print();
			   if(ext.getLeft() != null) waitingNode.push(ext.getLeft());
			   if(ext.getRight() != null) waitingNode.push(ext.getRight());
		   }
	   }
   }	 
	
}
