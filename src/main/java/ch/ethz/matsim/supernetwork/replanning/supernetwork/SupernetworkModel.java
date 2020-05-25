/**
 * 
 */
package ch.ethz.matsim.supernetwork.replanning.supernetwork;

import java.util.List;

import org.matsim.api.core.v01.population.PlanElement;


/**
 * @author stefanopenazzi
 *
 */
public interface SupernetworkModel {

	public List<? extends PlanElement> newPlan();
}
