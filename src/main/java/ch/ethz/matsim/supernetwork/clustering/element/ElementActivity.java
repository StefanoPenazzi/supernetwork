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
		setNextActivity();
	}
	
	public ElementActivity(Activity activity,Person person,Cluster<ElementActivity> cluster) {
		this.activity = activity;
		this.person = person;
		this.cluster = cluster;
		setNextActivity();
	}
	
	public Activity getActivity() {
		return this.activity;
	}
	
	public Activity getNextActivity() {
		return this.nextActivity;
	}
	
	public void setNextActivity() {
		
		  this.nextActivity = null;
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

	public double getDistNextActivity() {
		if(this.nextActivity == null) {
			return 0;
		}else {
		return Math.sqrt(Math.pow(activity.getCoord().getX() - nextActivity.getCoord().getX(),2) + 
				Math.pow(activity.getCoord().getY() - nextActivity.getCoord().getY(),2));
		}
	}
}
