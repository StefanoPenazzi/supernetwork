/**
 * 
 */
package ch.ethz.matsim.supernetwork.routing.manager.updatealgorithms;

import java.util.List;
import org.matsim.core.router.util.TravelTime;
import ch.ethz.matsim.supernetwork.routing.manager.RoutesContainer;
import ch.ethz.matsim.supernetwork.routing.network.cluster.elements.middlenetwork.Middlenetwork;


/**
 * @author stefanopenazzi
 *
 */
public interface UpdateAlgorithm {
	
	public List<UpdateAlgorithmOutput> getUpdate(RoutesContainer supernetworkRoutesContainer,List<Middlenetwork> middlenetworks,TravelTime travelTimes);

}
