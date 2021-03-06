/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.graph.tdsp;

import org.matsim.api.core.v01.population.Plan;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public abstract class OrdaRomOptimizationAlgorithm implements OptimizationAlgorithm {

	
	
	public abstract void init(GraphImpl graph);
	
	public abstract void setPermanentLabels(); 
	
	public abstract void setTemporaryLabels(); 
	
	public abstract Plan buildSolution(GraphImpl graph);
	

	 class LinkTimeKey implements Comparable<LinkTimeKey> {

		 private final int fromNode;
		 private final int toNode;
		 private final int time;
		 
		 public LinkTimeKey(int fromNode,int toNode, int time) {
			 this.fromNode = fromNode;
			 this.toNode = toNode;
			 this.time = time;
		 }
		 
		 public int getFromNode() {
			 return this.fromNode;
		 }
		 public int getToNode() {
			 return this.toNode;
		 }
		 
		public int getTime() {
			return this.time;
		}
		 
		@Override
		public int compareTo(LinkTimeKey arg0) {
			
			if(this.fromNode > arg0.getFromNode()) {
				return 1;
			}
			else if(this.fromNode < arg0.fromNode) {
				return -1;
			}
			else {
				if(this.toNode > arg0.getToNode()) {
					return 1;
				}
				else if(this.toNode < arg0.getToNode()) {
					return -1;
				}
				else {
					if(this.time > arg0.getTime()) {
						return 1;
					}
					else if(this.time< arg0.getTime()) {
						return -1;
					}
					else {
						return 0;
					}
				}
			}
		}
	}
}
