/**
 * 
 */
package ch.ethz.matsim.supernetwork.supernode;
import java.util.List;
import org.matsim.api.core.v01.Coord;
import ch.ethz.matsim.supernetwork.middlelink.Middlelink;

/**
 * @author stefanopenazzi
 *
 */
public class SupernodeImpl implements Supernode {

	private Coord coord;
	private List<Middlelink> inLinks;
	private List<Middlelink> outLinks;
	
	@Override
	public Coord getCoord() {
		
		return this.coord;
	}

	@Override
	public List<Middlelink> getInLinks() {
		
		return this.inLinks;
	}

	@Override
	public List<Middlelink> getOutLinks() {
		
		return this.outLinks;
	}

}
