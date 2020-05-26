/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.networkelements.supernode;

import java.util.List;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.NetworkUtils;

import ch.ethz.matsim.supernetwork.network.networkelements.middlelink.Middlelink;
import ch.ethz.matsim.supernetwork.network.networkelements.superlink.Superlink;

/**
 * @author stefanopenazzi
 *
 */
public class SupernodeImpl implements Supernode {

	private Coord coord;
	public List<Superlink> inSuperLinks;
	public List<Superlink> outSuperLinks;
	public List<Middlelink> outMiddleLinks;
	private static int id = 0;
	public Node node;
	
	public SupernodeImpl() {
		node = NetworkUtils.createNode(Id.create("sn_"+ Integer.toString(id) , Node.class));
		id++;
	}
	
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
		for(Middlelink ml : outMiddleLink) {
			node.addOutLink(ml.getLink());
		}
		
	}

	@Override
	public void setCoord(Coord coord) {
		this.coord = coord;
		
	}

	@Override
	public Node getNode() {
			
		return node;
	}

}
