/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernode;

import java.util.List;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.superlink.Superlink;

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
