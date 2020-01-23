/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules.Config;

import org.matsim.core.config.ReflectiveConfigGroup;
import org.matsim.core.config.ReflectiveConfigGroup.StringGetter;
import org.matsim.core.config.ReflectiveConfigGroup.StringSetter;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetworkConfigGroup extends ReflectiveConfigGroup  {

    public static final String GROUP_NAME = "Supernetwork";
	
	public static final String CLUSTERING_STRATEGY = "ClusteringStrategy";
	
	public static final String SUBNETWORK = "Subnetwork";
	
	public static final String MIDDLENETWORK = "Middlenetwork";
	
	public static final String SUPERNET = "Supernet";
	
	public static final String SIMULATIONDATACOLLECTION = "SimulationDataCollection";
	
	private String clusteringStrategy = "regionHierarchicalCS"; 
	
	private String subnetwork = "default"; 
	private String middlenetwork = "default"; 
	private String supernet = "default"; 
	private String simulationDataCollection = "default"; 
	
	public SupernetworkConfigGroup() {
		super(GROUP_NAME);
		// TODO Auto-generated constructor stub
	}
	
	@StringSetter(CLUSTERING_STRATEGY)
	public void setClusteringStrategy(String clusteringStrategy) {
		this.clusteringStrategy = clusteringStrategy;
	}

	@StringGetter(CLUSTERING_STRATEGY)
	public String getClusteringStrategy() {
		return clusteringStrategy;
	}
	
	@StringSetter(SUBNETWORK)
	public void setSubnetwork(String subnetwork) {
		this.subnetwork = subnetwork;
	}

	@StringGetter(SUBNETWORK)
	public String getSubnetwork() {
		return subnetwork;
	}
	
	@StringSetter(MIDDLENETWORK)
	public void setMiddlenetwork(String middlenetwork) {
		this.middlenetwork = middlenetwork;
	}

	@StringGetter(SUBNETWORK)
	public String getMiddlenetwork() {
		return middlenetwork;
	}
	
	@StringSetter(SUPERNET)
	public void setSupernet(String supernet) {
		this.supernet = supernet;
	}

	@StringGetter(SUPERNET)
	public String getSupernet() {
		return supernet;
	}
	
	@StringSetter(SIMULATIONDATACOLLECTION )
	public void setSimulationDataCollection(String simulationDataCollection) {
		this.simulationDataCollection = simulationDataCollection;
	}

	@StringGetter(SIMULATIONDATACOLLECTION )
	public String getSimulationDataCollection() {
		return simulationDataCollection;
	}

}
