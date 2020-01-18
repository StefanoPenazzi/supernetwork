/**
 * 
 */
package ch.ethz.matsim.supernetwork.subnetwork;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


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
public class SubnetworkDefaultImpl implements Subnetwork {

	private int id;
	
	private List<Node> nodes = new ArrayList();

	private List<Link> links = new ArrayList();
	
	SubnetworkDefaultImpl(int id,List<Node> nodes,List<Link> links) {
		this.id = id;
		this.nodes = nodes;
		this.links = links;
	}
	
	public int getId() {
		return this.id;
	}
	
	public  List<Node> getNodes(){
		return this.nodes;
	}
	
	public  List<Link> getLinks(){
		return this.links;
	}

}