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
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.scenario.*;
import org.matsim.utils.objectattributes.AttributeConverter;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
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
	private TreeRegions treeReg;
	

	public RegionsPlaneClustering(Scenario scenario) {
		regions = regionsFinder(wedgesFinder(scenario));
		regionsCentroid(regions);
		treeReg = new TreeRegions(regions);
		activitiesRegionsMatch(treeReg,scenario);
		stat();
		
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

	/**
	 * this method search in which region an activity is located 
	 */
	private void activitiesRegionsMatch(TreeRegions tr, Scenario scenario){
		for(Person p: scenario.getPopulation().getPersons().values()) {
			if(p.getPlans().size() >= 1) {
		     for(PlanElement pe: p.getPlans().get(0).getPlanElements()) {
		    	 if ( !(pe instanceof Activity) ) continue;
		    	 TreeRegionsNode trn = tr.nearestNeighbourSearch((Activity)pe);
		    	 trn.getReg().addActivity((Activity)pe);
		     }	
		   }
		}
	}
	
	private void regionsCentroid(List<Region> regions){
		
		for(Region r: regions) {
			double x = 0;
			double y = 0;
			double area = 0;
			for(int j =0;j<r.getNodes().size()-1;j++) {
				double currX = r.getNodeCoord(j).getX();//ln.get(j).getCoord().getX();
				double currXPlusOne = r.getNodeCoord(j+1).getX();//ln.get(j+1).getCoord().getX();
				double currY = r.getNodeCoord(j).getY();
				double currYPlusOne = r.getNodeCoord(j+1).getY();
				
				x = x + ((currX + currXPlusOne)*(currX* currYPlusOne - currXPlusOne*currY));
				y = y + ((currY + currYPlusOne)*(currX* currYPlusOne - currXPlusOne*currY));
				area = area + ((currX*currYPlusOne) - (currXPlusOne*currY));
				
			}
			area = area/2;
			x = (1/(6*area))*x;
			y = (1/(6*area))*y;
			
			r.setArea(Math.abs(area));
			r.setCentroidCoord(new Pair<Double,Double>(x,y));
		}

	}
	
	public void stat() {
		   actFreqAnalysis(10);
		   distFreqAnalysis(100);
		   areaFreqAnalysis(10000);
    }
   
   public void actFreqAnalysis(int range) {
	  
	   Collections.sort(regions, new Comparator<Region>(){
			@Override
			  public int compare(Region u1, Region u2) {
				int c;
				
			    c = u1.getActivities().size() == u2.getActivities().size()? 0 : u1.getActivities().size() < u2.getActivities().size() ? -1:1; 
			    return c;
			  }
		});
	   
	   int maxAct = regions.get(regions.size()-1).getActivities().size();
	   int minAct = 0;
	   if (range == 0 || range>maxAct) return;
	   int classes = (int) (Math.ceil(maxAct/range) + 1);
	   int[][] res = new int[classes][3];
	   res[0][0] = 0;
	   res[0][1] = 0;
	   for(int i = 1;i<classes;i++) {
		   res[i][0] = i*range - (range-1);
		   res[i][1] = i*range;
	   }
	   
	   for(Region r:regions) {
		  int pos = (int) (Math.ceil(r.getActivities().size()/range));
		  res[pos][2] =  res[pos][2] + 1; 
	   }
	   
	   int regWithAct =0;
	   for(int i = 0;i<classes;i++) {
		   regWithAct += res[i][2];
	   }
	   
	   //print stat
	   System.out.println("N. of regions with more than 1 act = "+ regWithAct);
	   System.out.println("Freq analysis");
	   for(int i = 0;i<classes;i++) {
		   System.out.println(" Range ( "+ res[i][0]+ " - "+ res[i][1]+ " ) -> "+ res[i][2] );
	   }
   }
   
   public void distFreqAnalysis(double range) {
		  
	   if (range == 0 || range> 4000) return;
	   int classes = (int) (Math.ceil(4000/range) + 1);
	   double[][] res = new double[classes][3];
	   for(int i = 0;i<classes;i++) {
		   int j = i+1;
		   res[i][0] = j*range - (range-0.9);
		   res[i][1] = j*range;
	   }
	   for(Region r:regions) {
		   double avgDist =0;
		   for(Activity act:r.getActivities()){
			   avgDist += Math.sqrt( Math.pow((r.centroidCoord.getValue0() - act.getCoord().getX() ),2) + Math.pow((r.centroidCoord.getValue1() - act.getCoord().getY() ),2));
		   }
		   avgDist = avgDist/ r.getActivities().size();
		   int pos = (int) (Math.ceil(avgDist/range));
		   if(pos>classes-1) {
			   res[classes-1][2] =  res[classes-1][2] + 1; 
		   }
		   else {
			   res[pos][2] =  res[pos][2] + 1; 
		   }
		   
	    }
	   
	   //print stat
	   System.out.println("Average dist from centroid");
	   System.out.println("Freq analysis");
	   for(int i = 0;i<classes;i++) {
		   System.out.println(" Range ( "+ res[i][0]+ " - "+ res[i][1]+ " ) -> "+ res[i][2] );
	   }
   }
   
   public void areaFreqAnalysis(double range) {
		  
	   if (range == 0 || range> 1000000) return;
	   int classes = (int) (Math.ceil(1000000/range) + 1);
	   double[][] res = new double[classes][3];
	   for(int i = 0;i<classes;i++) {
		   int j = i+1;
		   res[i][0] = j*range - (range-0.9);
		   res[i][1] = j*range;
	   }
	   for(Region r:regions) {
		   int pos = (int) (Math.ceil(r.getArea()/range));
		   if(pos>classes-1) {
			   res[classes-1][2] =  res[classes-1][2] + 1; 
		   }
		   else {
			   res[pos][2] =  res[pos][2] + 1; 
		   }
		   
	    }
	   
	   //print stat
	   System.out.println("Regions area");
	   System.out.println("Freq analysis");
	   for(int i = 0;i<classes;i++) {
		   System.out.println(" Range ( "+ res[i][0]+ " - "+ res[i][1]+ " ) -> "+ res[i][2] );
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
		private List<Activity> activities = new ArrayList();
		
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
		public Coord getNodeCoord(int i){
			return nodes.get(i).getCoord();
		}
		
		public List<Activity> getActivities(){
			return activities;
		}
		public void addActivity(Activity act) {
			activities.add(act);
		}
		
		public void print() {
			String nn = "";
			for(Node n:nodes) {
				nn = nn + n.getId().toString() + " - ";
			}
			System.out.println(nn);
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
			 double res = Math.pow(this.reg.getCentroidCoord().getValue0() - act.getCoord().getX(),2) + Math.pow(this.reg.getCentroidCoord().getValue1() - act.getCoord().getY(),2);
			 return res;
		 }
		 
		 public TreeRegionsNode findParent(Activity act) {
			 TreeRegionsNode parent = null;
			 TreeRegionsNode next = this;
			 boolean split;
			 while (next != null) {
				 split = next.lr;
				 parent = next;
				 if(split) {
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
					 +" <(left) "+ sLeft + " <>(X(True) or Y(False)) : "+ lr);
		 }
	}	 
	
   private class TreeRegions{
	   
	   private TreeRegionsNode root = null;
	   List<Region> regions;
	   TreeRegionsNode checkedNodes[];
	   TreeRegionsNode nearestNeighbour;
	   int checkedNodesCounter;
	   int nBoundary;
	   double xMin, xMax;
	   double yMin, yMax;
	   double dMin;
	   boolean xMaxBoundary, xMinBoundary;
	   boolean yMaxBoundary, yMinBoundary;
	   
	   
	   public TreeRegions(List<Region> regions){
		   this.regions = regions;
		   checkedNodes = new TreeRegionsNode[regions.size()];
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
				 return newTreeNode(newReg,lr,child);
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
		   
		   checkedNodesCounter = 0;
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
	 
	        checkedNodes[checkedNodesCounter++] = node;
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
	        for (int n = 0; n < checkedNodesCounter; n++)
	            checkedNodes[n].checked = false;
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
