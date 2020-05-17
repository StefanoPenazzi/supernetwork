/**
 * 
 */
package ch.ethz.matsim.supernetwork.networkelements.supernode;

import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.networkelements.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.networkelements.superlink.Superlink;

/**
 * @author stefanopenazzi
 *
 */
public interface Supernode {
	
	public Coord getCoord();
	public List<Superlink> getInSuperLinks();
	public List<Superlink> getOutSuperLinks();
	public List<Middlelink> getOutMiddleLinks();
	
	public void setCoord(Coord coord);
	public void setInSuperLinks(List<Superlink> inSuperLink);
	public void setOutSuperLinks(List<Superlink> outSuperLink);
	public void setOutMiddleLinks(List<Middlelink> outMiddleLink);
	
	public Node getNode();
	

}
