/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.network.cluster.elements.supernode;

/**
 * @author stefanopenazzi
 *
 */
public class TimeSupernodeImpl implements TimeSupernode {

	private final Supernode supernode;
	
	public TimeSupernodeImpl(Supernode supernode) {
		this.supernode = supernode;
	}
	
	@Override
	public Supernode getSupernode() {
		// TODO Auto-generated method stub
		return this.supernode;
	}

}
