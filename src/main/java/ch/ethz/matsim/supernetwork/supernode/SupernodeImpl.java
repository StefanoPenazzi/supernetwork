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
public class SupernodeImpl implements Supernode {

	private Coord coord;
	private List<Superlink> inLinks;
	private List<Superlink> outLinks;
	
	@Override
	public Coord getCoord() {
		
		return this.coord;
	}

	@Override
	public List<Superlink> getInLinks() {
		
		return this.inLinks;
	}

	@Override
	public List<Superlink> getOutLinks() {
		
		return this.outLinks;
	}

}
