/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.element;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;

/**
 * @author stefanopenazzi
 *
 */
public class ElementActivity implements Element {
	
	private Activity activity;
	private Person person;
	private Cluster<ElementActivity> cluster;
	
	public ElementActivity(Activity activity,Person plan) {
		this.activity = activity;
		this.person = person;
		this.cluster = null;
	}
	
	public ElementActivity(Activity activity,Person plan,Cluster<ElementActivity> cluster) {
		this.activity = activity;
		this.person = person;
		this.cluster = cluster;
	}
	
	public Activity getActivity() {
		return this.activity;
	}
	
	public Person getPlan() {
		return this.person;
	}
	
	public Cluster<ElementActivity> getCluster() {
		return this.cluster;
	}
	public void setCluster(Cluster<ElementActivity> cluster) {
		this.cluster = cluster;
	}

}
