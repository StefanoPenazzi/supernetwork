/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.networkelements.superlink;

import org.matsim.api.core.v01.network.Node;

import ch.ethz.matsim.supernetwork.network.networkelements.supernode.Supernode;
import ch.ethz.matsim.supernetwork.network.networkelements.supernode.TimeSupernode;

/**
 * @author stefanopenazzi
 *
 */
public class SuperlinkImpl implements Superlink  {
	
	
	private TimeSupernode from;
	private TimeSupernode to;
	private double weight;
	
	public SuperlinkImpl(TimeSupernode from,TimeSupernode to,double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	@Override
	public TimeSupernode getFrom() {
		// TODO Auto-generated method stub
		return this.from;
	}

	@Override
	public TimeSupernode getTo() {
		// TODO Auto-generated method stub
		return this.to;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return this.weight;
	}

}
