/**
 * 
 */
package ch.ethz.matsim.supernetwork.simulationData.trafficData.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;

import com.google.inject.Inject;

/**
 * @author stefanopenazzi
 *
 */
public final class TrafficDataContainerDefaultImpl implements TrafficDataContainer {

	private Network network;
	private static Map<Id<Link>,List<Integer>> inputFlows = new TreeMap<>();
	private static Map<Id<Link>,List<Integer>> outputFlows = new TreeMap<>();
	private static Map<Id<Link>,List<TravelTime>> linksTravelTime = new TreeMap<>();

	@Inject
	TrafficDataContainerDefaultImpl (Network network){
		this.network = network;
		for(Link l: network.getLinks().values()) {
			inputFlows.put(l.getId(), new ArrayList());
			outputFlows.put(l.getId(), new ArrayList());
		}
	}
	
	public Map<Id<Link>,List<Integer>> getInputFlows(){
		return this.inputFlows;
	}
	
	public Map<Id<Link>,List<Integer>> getOutputFlows(){
		return this.outputFlows;
	}
	
	public Map<Id<Link>,List<TravelTime>> getLinksTravelTime(){
		return this.linksTravelTime;
	}
	
	public void linksTravelTimeComputation() {
		List<List<Integer>> in =  new ArrayList<>(inputFlows.values());
		List<List<Integer>> out = new ArrayList<>(outputFlows.values());
		List<List<TravelTime>> res = new ArrayList();
		
		for(int i = 0;i< out.size();i++ ) {
			List<TravelTime> traveltime = new ArrayList();
			int min = Math.min(in.get(i).size(), out.get(i).size());
			for(int j = 0;j<min;j++) {
				traveltime.add(new TravelTime(in.get(i).get(j),out.get(i).get(j) - in.get(i).get(j)));
			}
			res.add(traveltime);	
		}
		int i = 0;
		for(Link l: network.getLinks().values()) {
			linksTravelTime.put(l.getId(), res.get(i));
			i++;
		}
	}
	
	public int getLinkTravelTime(Id<Link> id, int startTime) {
		int travelTime = 0;
		List<TravelTime> tt = linksTravelTime.get(id);
		if(tt.size()>0) {
			TravelTime comp = new TravelTime(startTime,0);
			travelTime = tt.get(Collections.binarySearch(tt, comp)).getTravelTime();
		}
		else {
			Link l = network.getLinks().get(id);
			travelTime = (int)(l.getLength()/l.getFreespeed()); //TODO unit of measure?
		}
		return travelTime;
	}
}
