/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.element;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;

/**
 * @author stefanopenazzi
 *
 */
public class ElementActivity implements Element {
	
	private Activity activity;
	private Person person;
	
	public ElementActivity(Activity activity,Person plan) {
		this.activity = activity;
		this.person = person;
	}
	
	public Activity getActivity() {
		return this.activity;
	}
	
	public Person getPlan() {
		return this.person;
	}

}
