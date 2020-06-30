/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.graph.tdsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.PopulationFactory;

import ch.ethz.matsim.supernetwork.network.database.manager.ContainerManager;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.PlanModel;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Node;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.scoring.ScoringFunctionsForPopulationGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdsp.TdspGraphOrdaRom;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalGraph;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalLink;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal.TdspIntermodalNode;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public abstract class OrdaRomOptimizationAlgorithm implements OptimizationAlgorithm {

	
	
	public abstract void init(GraphImpl graph);
	
	public abstract void setPermanentLabels(); 
	
	public abstract void setTemporaryLabels(); 
	
	public abstract boolean buildSolution(GraphImpl graph);
	

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
