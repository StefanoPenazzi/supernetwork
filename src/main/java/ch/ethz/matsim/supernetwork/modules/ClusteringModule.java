/**
 * 
 */
package ch.ethz.matsim.supernetwork.modules;

import java.util.Map;
import com.google.inject.Provides;
import ch.ethz.matsim.supernetwork.cluster_analysis.clusters_container.ClustersContainer;
import ch.ethz.matsim.supernetwork.networkmodels.clustering_models.ClusteringModelFactory;
import ch.ethz.matsim.supernetwork.networkmodels.clustering_models.RegionHierarchicalCS;
import ch.ethz.matsim.supernetwork.modules.Config.SupernetworkConfigGroup;

/**
 * @author stefanopenazzi
 *
 */
public class ClusteringModule extends AbstractSupernetworkExtension {

	public static final String REGION_HIERARCHICAL_CS   = "RegionHierarchicalCS";
	public static final String REGION_CS   = "RegionCS";
	public static final String HIERARCHICAL_CS   = "HierarchicalCS";
	
	@Override
	protected void installExtension() {
		bindClusteringModelFactory(REGION_HIERARCHICAL_CS).to(RegionHierarchicalCS.Factory.class);
		
	}
	
	@Provides
	public ClustersContainer provideClustersContainer(Map<String,ClusteringModelFactory> msc,SupernetworkConfigGroup supernetworkConfigGroup) {
		switch (supernetworkConfigGroup.getClusteringStrategy()) {
		case REGION_HIERARCHICAL_CS:
			 return msc.get(REGION_HIERARCHICAL_CS).generateClusteringModel() ;
		case REGION_CS:
			 return null;
		case HIERARCHICAL_CS:
			return null;
		default:
			//throw new IllegalStateException("The param modeChainGenerator in the module DiscreteModeChoice is not allowed.");
			return msc.get(REGION_HIERARCHICAL_CS).generateClusteringModel() ;
		}
	}
}
