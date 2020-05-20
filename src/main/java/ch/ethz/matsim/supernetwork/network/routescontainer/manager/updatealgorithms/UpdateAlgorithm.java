/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.routescontainer.manager.updatealgorithms;

import java.util.List;
import java.util.Map;

import org.matsim.core.router.util.TravelTime;

import ch.ethz.matsim.supernetwork.network.routescontainer.SupernetworkRoutesContainer;
import ch.ethz.matsim.supernetwork.networkelements.middlenetwork.Middlenetwork;

/**
 * @author stefanopenazzi
 *
 */
public interface UpdateAlgorithm {
	
	public List<UpdateAlgorithmOutput> getUpdate(SupernetworkRoutesContainer supernetworkRoutesContainer,List<Middlenetwork> middlenetworks,TravelTime travelTimes);

}
