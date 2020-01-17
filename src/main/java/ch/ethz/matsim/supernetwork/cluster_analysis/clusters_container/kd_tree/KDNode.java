/**
 * 
 */
package ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.kd_tree;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;

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
	 private boolean checked = false;
	 
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
	 public KDNode getChild() {
		 return child;
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
	 public void setChild(KDNode right) {
		 this.child= child;
	 }
	 public boolean getChecked() {
		 return checked;
	 }
	 public void setChecked(boolean checked) {
		 this.checked = checked;
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
	 
	        if(child.getLr()) // x
			 { 
				 if(child.getCluster().getCentroid().getX()<=newNode.getCluster().getCentroid().getX()){
					 child.setRight(newNode);
				 }
				 else{
					 child.setLeft(newNode);
				 }
			 }
			 else
			 {
				 if(child.getCluster().getCentroid().getY()<=newNode.getCluster().getCentroid().getY()){
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
	 public double distance2(Cluster cl1, Cluster cl2) {
		 double dist = Math.pow(cl1.getCentroid().getX()-cl2.getCentroid().getX(),2) + Math.pow(cl1.getCentroid().getY()-cl2.getCentroid().getY(),2);
		 return dist;
	 }
	 public void print(){
		 String sRight = this.getRight() == null ? "null" : "x = "+this.getRight().getCluster().getCentroid().getX()+ " y = "+this.getRight().getCluster().getCentroid().getY();
		 String sLeft = this.getLeft() == null ? "null" : "x = " + this.getLeft().getCluster().getCentroid().getX()+ " y = "+this.getLeft().getCluster().getCentroid().getY();
		 System.out.println("--> x = "+this.getCluster().getCentroid().getX()+" y = "+ this.getCluster().getCentroid().getY()+" >(right) " + sRight
				 +" <(left) "+ sLeft + " <>(X(True) or Y(False)) : "+ lr);
	 }
}
