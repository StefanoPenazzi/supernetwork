/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernode;

import java.util.List;

import org.matsim.api.core.v01.Coord;

import ch.ethz.matsim.supernetwork.superlink.Superlink;

/**
 * @author stefanopenazzi
 *
 */
public interface Supernode {
	
	public Coord getCoord();
	public List<Superlink> getInLinks();
	public List<Superlink> getOutLinks();
	

}
