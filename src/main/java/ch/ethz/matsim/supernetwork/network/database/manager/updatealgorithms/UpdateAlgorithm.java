/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.database.manager.updatealgorithms;

import java.util.List;
import java.util.Map;

import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.supernetwork.network.database.containers.RoutesContainer;
import ch.ethz.matsim.supernetwork.network.networkelements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface UpdateAlgorithm {
	
	public List<UpdateAlgorithmOutput> getUpdate(RoutesContainer supernetworkRoutesContainer,List<Middlenetwork> middlenetworks,TravelTime travelTimes);

}