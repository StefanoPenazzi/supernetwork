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
	
	public static final String CLUSTERING_STRATEGY = "clusteringStrategy";
	
	private String clusteringStrategy = "regionHierarchicalCS"; 
	
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

}
