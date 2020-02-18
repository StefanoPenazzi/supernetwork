/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernode;
import java.util.List;
import org.matsim.api.core.v01.Coord;
import ch.ethz.matsim.supernetwork.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.superlink.Superlink;

/**
 * @author stefanopenazzi
 *
 */
public class SupernodeImpl implements Supernode {

	private Coord coord;
	public List<Superlink> inSuperLinks;
	public List<Superlink> outSuperLinks;
	public List<Middlelink> outMiddleLinks;
	
	@Override
	public Coord getCoord() {
		
		return this.coord;
	}

	@Override
	public List<Middlelink> getOutMiddleLinks() {
		
		return this.outMiddleLinks;
	}

	@Override
	public List<Superlink> getInSuperLinks() {
		
		return this.inSuperLinks;
	}

	@Override
	public List<Superlink> getOutSuperLinks() {
		return this.outSuperLinks;
	}

	@Override
	public void setInSuperLinks(List<Superlink> inSuperLink) {
		this.inSuperLinks = inSuperLink;
		
	}

	@Override
	public void setOutSuperLinks(List<Superlink> outSuperLink) {
		this.outSuperLinks =  outSuperLink;
		
	}

	@Override
	public void setOutMiddleLinks(List<Middlelink> outMiddleLink) {
		this.outMiddleLinks = outMiddleLink;
		
	}

	@Override
	public void setCoord(Coord coord) {
		this.coord = coord;
		
	}

}
