/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.clustersContainer;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;


/**
 * @author stefanopenazzi
 *
 */
public class KDNode {

	 private Cluster cluster = null;
	 private KDNode child = null;
	 private KDNode left = null;
	 private KDNode right = null;
	 private boolean lr = false;
	 private boolean checked;
	 
	 public KDNode(Cluster cluster,boolean lr,KDNode child){
		 this.cluster= cluster;
		 this.lr = lr;
		 this.child = child;
	 }
	 
	 public Cluster getCluster() {
		 return cluster;
	 }
	 public KDNode getLeft() {
		 return left;
	 }
	 public KDNode getRight() {
		 return right;
	 }
	 public boolean getLr() {
		 return lr;
	 }
	 public void setLeft(KDNode left) {
		 this.left = left;
	 }
	 public void setRight(KDNode right) {
		 this.right = right;
	 }
	 
	 public KDNode FindChild(Cluster cl)
	 {
		 KDNode parent = null;
		 KDNode next = this;
		 boolean split;
		 while (next != null) {
			 split = next.lr;
			 parent = next;
			 if(split) {
				 if(next.getCluster().getCentroid().getX()<=cl.getCentroid().getX()){
					 next = next.getRight();
				 }
				 else{
					 next = next.getLeft();
				 }
			 }
			 else {
				 if(next.getCluster().getCentroid().getY()<=cl.getCentroid().getY()){
					 next = next.getRight();
				 }
				 else{
					 next = next.getLeft();
				 }
			 }
		 }
		 return parent;
	 }
	 
	 public KDNode Insert(Cluster cl)
	 {
		//x = new double[2];
	        KDNode child = FindChild(cl);
	        if (equal(cl, child.getCluster()) == true)
	            return null;
	 
	        KDNode newNode = new KDNode(cl,!child.getLr(),child);
	 
	        if(lr) // x
			 { 
				 if(newNode.getCluster().getCentroid().getX()<=newNode.getCluster().getCentroid().getX()){
					 child.setRight(newNode);
				 }
				 else{
					 child.setLeft(newNode);
				 }
			 }
			 else
			 {
				 if(newNode.getCluster().getCentroid().getY()<=newNode.getCluster().getCentroid().getY()){
					 child.setRight(newNode);
				 }
				 else{
					 child.setLeft(newNode);
				 }
			 }
	 
	        return newNode;
	 }
	 private boolean equal(Cluster cl1, Cluster cl2) {
		 if(cl1.getCentroid().getX() == cl2.getCentroid().getX() && cl1.getCentroid().getY() == cl2.getCentroid().getY()) {
			 return true;
		 }
		 return false;
	 }
	 private double distance2(Cluster cl1, Cluster cl2) {
		 double dist = Math.pow(cl1.getCentroid().getX()-cl2.getCentroid().getX(),2) + Math.pow(cl1.getCentroid().getY()-cl2.getCentroid().getY(),2);
		 return dist;
	 }
}
