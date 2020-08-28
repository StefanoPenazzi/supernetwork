/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster_element;

import org.matsim.api.core.v01.population.Person;
import org.matsim.facilities.Facility;

import ch.ethz.matsim.supernetwork.routing.network.cluster.clusteranalysis.cluster.Cluster;



/**
 * @author stefanopenazzi
 *
 */
public class ElementActivity implements Element {
	
	private final Facility facility;
	private final Facility nextFacility;
	private final Person person;
	private Cluster<ElementActivity> cluster;
	private final double startTime;
	
	
	public ElementActivity(Facility facility,Facility nextFacility ,Person person,Cluster<ElementActivity> cluster,double startTime) {
		this.facility = facility;
		this.person = person;
		this.nextFacility = nextFacility;
		this.cluster = cluster;
		this.startTime = startTime;
	}
	
	public Facility getFacility() {
		return this.facility;
	}
	
	public Facility getNextFacility() {
		return this.nextFacility;
	}
	
	public Person getPlan() {
		return this.person;
	}
	
	public Cluster<ElementActivity> getCluster() {
		return this.cluster;
	}
	
	public double getStartTime() {
		return this.startTime;
	}
	
	public void setCluster(Cluster<ElementActivity> cluster) {
		this.cluster = cluster;
	}
	
	public double getDistNextFacility() {
		if(this.nextFacility == null) {
			return 0;
		}else {
//		return Math.sqrt(Math.pow(facility.getCoord().getX() - nextFacility.getCoord().getX(),2) + 
//				Math.pow(facility.getCoord().getY() - nextFacility.getCoord().getY(),2));
//		}
			//TODO
			return 0;
		}
	}
}
