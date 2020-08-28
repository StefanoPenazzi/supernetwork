/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.clusters_container.kd_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.matsim.api.core.v01.Coord;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.CALNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.centroid.ClusterActivitiesLocation;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.clusters_container.ClustersContainer;

/**
 * @author stefanopenazzi
 *
 */
public class KDTreeClustersContainer implements ClustersContainer<ClusterActivitiesLocation,ElementActivity>{

	private KDNode root = null;
    private List<ClusterActivitiesLocation> clusters = new ArrayList();
    private KDNode checkedNodes[];
    private KDNode nearestNeighbour;
    private int checkedNodesCounter;
    private int nBoundary;
    private double xMin, xMax;
    private double yMin, yMax;
    private double dMin;
    private boolean xMaxBoundary, xMinBoundary;
    private boolean yMaxBoundary, yMinBoundary;
	
	public KDTreeClustersContainer(KDNode root, int cn) {
		this.root = root;
		this.checkedNodes = new KDNode[cn];
	}

	public boolean add(ClusterActivitiesLocation x)
    {
		clusters.add(x);
        if (root == null)
        {
            root = new KDNode(x, false,null);
           
        } else
        {
            KDNode pNode = root.Insert(x);
        }
 
        return true;
    }
	
    public KDNode nearestNeighbourSearch(Coord coord) {
		if (this.root == null)
            return null;
	   
	   CALNetworkRegionImpl cn = new CALNetworkRegionImpl(0,null,coord);
	   checkedNodesCounter = 0;
	   KDNode child = root.FindChild(cn);
	   nearestNeighbour = child;
	   dMin = child.distance2(cn, child.getCluster());
	   
	   if(child.getCluster().getCentroid().getX() == coord.getX() && child.getCluster().getCentroid().getY() == coord.getY()) {
		   return child;
	   }
	   searchChild(child, coord);
	   //clean for the next query
	   uncheck();
	   return nearestNeighbour; 
	}
	
	public KDNode searchChild(KDNode child, Coord coord)
    {
	    xMin = xMax = yMin = yMax =0;
	    xMaxBoundary = xMinBoundary = yMaxBoundary = yMinBoundary = false;
        nBoundary = 0;
         
 
        KDNode searchRoot = child;
        while (child != null && (nBoundary != 4))
        {
            checkSubtree(child, coord);
            searchRoot = child;
            //backtracking
            child = child.getChild();
        }
 
        return searchRoot;
    }
	
	public void checkSubtree(KDNode node, Coord coord)
    {
        if ((node == null) || node.getChecked())
            return;
 
        checkedNodes[checkedNodesCounter++] = node;
        node.setChecked(true);
        setBoundingCube(node, coord);
 
        boolean lr = node.getLr();
        double dist;
        if (lr) {
        	dist = node.getCluster().getCentroid().getX() - coord.getX();
        }
        else {
        	dist = node.getCluster().getCentroid().getY() - coord.getY();
        }
        
        if (dist * dist > dMin)
        {
        	if(lr) {
        		if (node.getCluster().getCentroid().getX() > coord.getX())
	                checkSubtree(node.getLeft(), coord);
	            else
	                checkSubtree(node.getRight(), coord);
        	}
        	else {
        		if (node.getCluster().getCentroid().getY() > coord.getY())
	                checkSubtree(node.getLeft(), coord);
	            else
	                checkSubtree(node.getRight(), coord);
        	}
            
        } else
        {
            checkSubtree(node.getLeft(), coord);
            checkSubtree(node.getRight(), coord);
        }
    }
	
	public void setBoundingCube(KDNode node, Coord coord)
    {
        if (node == null)
            return;
        double d = 0;
        double dx;
        double dy;
        
        //xbound
        dx = node.getCluster().getCentroid().getX() - coord.getX();
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
        dy = node.getCluster().getCentroid().getY() - coord.getY();
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
	
	private void uncheck()
    {
        for (int n = 0; n < checkedNodesCounter; n++)
            checkedNodes[n].setChecked(false);
    }
    
    public void addCluster(ClusterActivitiesLocation c) {
		
	}
	public void deleteCluster(ClusterActivitiesLocation c) {
		
	}
	public void merge2Cluster(ClusterActivitiesLocation c1, ClusterActivitiesLocation c2) {
		
	}
	public ClusterActivitiesLocation[] splitCluster(ClusterActivitiesLocation c) {
		ClusterActivitiesLocation[] result = new ClusterActivitiesLocation[2];
		return result;
	}
	public ClusterActivitiesLocation getCluster(Coord c) {
		ClusterActivitiesLocation result = null;
		return result;
	}
	public ClusterActivitiesLocation getCluster(int id) {
		ClusterActivitiesLocation result = null;
		return result;
	}
	
	public List<ClusterActivitiesLocation> getClusters(){
		return this.clusters;
	}
	
	public void print(){

		   Stack<KDNode> waitingNode = new Stack();
		   waitingNode.push(root);
		   while(!waitingNode.isEmpty()){
			   KDNode ext = waitingNode.pop();
			   ext.print();
			   if(ext.getLeft() != null) waitingNode.push(ext.getLeft());
			   if(ext.getRight() != null) waitingNode.push(ext.getRight());
		   }
	   }

	@Override
	public ClusterActivitiesLocation getCluster(ElementActivity element) {
		// TODO Auto-generated method stub
		return null;
	}
}
