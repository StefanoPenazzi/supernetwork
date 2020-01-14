/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.utils.objectattributes.attributable.Attributes;

/**
 * @author stefanopenazzi
 *
 */
public class SubnetworkDefaultImpl implements Subnetwork,Network {

	@Override
	public Attributes getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Id<Node>, ? extends Node> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Id<Link>, ? extends Link> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getCapacityPeriod() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEffectiveLaneWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addNode(Node nn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLink(Link ll) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node removeNode(Id<Node> nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link removeLink(Id<Link> linkId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCapacityPeriod(double capPeriod) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEffectiveCellSize(double effectiveCellSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEffectiveLaneWidth(double effectiveLaneWidth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEffectiveCellSize() {
		// TODO Auto-generated method stub
		return 0;
	}


}
