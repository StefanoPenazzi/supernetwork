/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.tdspIntermodal;

import java.util.List;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.GraphImpl;
import ch.ethz.matsim.supernetwork.network.planoptimization.models.graph.elements.Node;
import ch.ethz.matsim.supernetwork.network.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public class TdspIntermodalGraph extends GraphImpl {

	/**
	 * @param optimizationAlgorithm
	 */ 
	private final Person person;
	private int rootId;
	private int destinationId;
	private double[] startTimes;
	
	public TdspIntermodalGraph(Person person) {
		super();
		this.person = person;
	}
	
	public void print() {
		for(Node n: this.getNodes()) {
			TdspIntermodalNode node = (TdspIntermodalNode)n;
			node.print();
		}
	}

	@Override
	public Person getPerson() {
		return this.person;
	}
	
	public int getRootId() {
		return this.rootId;
	}

	public int getDestinationId() {
		return this.destinationId;
	}
	
	public void setRootId(int rootId) {
		this.rootId = rootId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}
	
	public double[] getStartTimes() {
		return this.startTimes;
	}
	
	public void setStartTimes(double[] startTimes) {
		this.startTimes = startTimes;
	}
}
