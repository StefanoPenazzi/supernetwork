/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models;

import java.util.List;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.optimizationAlgorithms.OptimizationAlgorithm;

/**
 * @author stefanopenazzi
 *
 */
public interface PlanModel {

	public Person getPerson();
	public Plan getPlan();
}
