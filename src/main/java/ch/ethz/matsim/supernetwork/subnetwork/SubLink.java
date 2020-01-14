/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import java.util.Set;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;
import org.matsim.utils.objectattributes.attributable.Attributes;

/**
 * @author stefanopenazzi
 *
 */
public class SubLink implements Link {

	@Override
	public Coord getCoord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attributes getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Id<Link> getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setFromNode(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setToNode(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node getToNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getFromNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getNumberOfLanes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getNumberOfLanes(double time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFreespeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFreespeed(double time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCapacity(double time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFreespeed(double freespeed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLength(double length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNumberOfLanes(double lanes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCapacity(double capacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAllowedModes(Set<String> modes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getAllowedModes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getFlowCapacityPerSec() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFlowCapacityPerSec(double time) {
		// TODO Auto-generated method stub
		return 0;
	}

}
