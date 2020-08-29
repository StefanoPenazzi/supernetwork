/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.tdspIntermodal;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.population.Activity;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Link;
import ch.ethz.matsim.dedalo.supernetwork.planoptimization.models.graph.elements.Node;

/**
 * @author stefanopenazzi
 *
 */
public class TdspIntermodalNode implements Node {

	private int id;
	private List<Link> inLinks;
	private List<Link> outLinks;
	private Activity activity;
	private String mode;
	public String nodeType;
	private int position;
	private double duration;
	
	
	public TdspIntermodalNode(int id,Activity activity, String mode, String nodeType ,double duration,int position ) {
		this.id = id;
		this.activity = activity;
		this.mode = mode;
		this.position = position;
		this.nodeType = nodeType;
		this.duration = duration;
		inLinks = new ArrayList<>();
		outLinks = new ArrayList<>();
	}
	
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public List<Link> getInLinks() {
		return this.inLinks;
	}

	@Override
	public List<Link> getOutLinks() {
		return this.outLinks;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		
	}

	public Activity getActivity() {
		return this.activity;
	}
	
	public String getMode() {
		return this.mode;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public double getDuration() {
		return this.duration;
	}
	
	public String getNodeType() {
		return this.nodeType;
	}

	@Override
	public void addInLink(Link l) {
		inLinks.add(l);
		
	}

	@Override
	public void addOutLink(Link l) {
		outLinks.add(l);
		
	}
	
	public void print() {
		System.out.println("Node id = "+ this.id + " ; type = " + this.nodeType + "; position = "+this.position+ " ; mode = "+ this.mode);
		System.out.println(">>> InLinks : ");
		for(Link l: this.inLinks) {
			TdspIntermodalLink link = (TdspIntermodalLink)l;
			System.out.println(">>>>>> Id = "+link.getFromNodeId()+ " ; mode = "+ link.getMode() + " ; type = "+ link.getType()+" ; duration = "+ link.getDuration() + " ; uf = "+link.getUtility());
		}
		System.out.println(">>> OutLinks : ");
		for(Link l: this.outLinks) {
			TdspIntermodalLink link = (TdspIntermodalLink)l;
			System.out.println(">>>>>> Id = "+link.getToNodeId()+ " ; mode = "+ link.getMode() + " ; type = "+ link.getType()+" ; duration = "+ link.getDuration() + " ; uf = "+link.getUtility()); 
		}
		System.out.println("");
	}
	
}
