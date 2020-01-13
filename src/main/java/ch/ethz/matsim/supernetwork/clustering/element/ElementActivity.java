/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.element;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Plan;

/**
 * @author stefanopenazzi
 *
 */
public class ElementActivity implements Element {
	
	private Activity activity;
	private Plan plan;
	
	public ElementActivity(Activity activity,Plan plan) {
		this.activity = activity;
		this.plan = plan;
	}
	
	public Activity getActivity() {
		return this.activity;
	}
	
	public Plan getPlan() {
		return this.plan;
	}

}
