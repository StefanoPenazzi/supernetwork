/**
 * 
 */
package ch.ethz.matsim.supernetwork.clustering.element;

import java.util.List;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;

/**
 * @author stefanopenazzi
 *
 */
public class ElementActivity implements Element {
	
	private Activity activity;
	private Activity nextActivity;
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
	
	public Activity getNextActivity() {
		return this.nextActivity;
	}
	
	public void setNextActivity() {
		
		  List<PlanElement> le = person.getPlans().get(0).getPlanElements();
		  int activityListIndex = le.indexOf(activity);
		  if(activityListIndex < le.size()-1) {
		  for(int j = activityListIndex+1; j<le.size() ;++j ) { 
			  if ( le.get(j) instanceof Activity) { 
				  nextActivity = (Activity) le.get(j);
		          break; 
		         } 
			  }
		  }
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
