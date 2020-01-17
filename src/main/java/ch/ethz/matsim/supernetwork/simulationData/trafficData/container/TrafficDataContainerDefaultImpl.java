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
	private static Map<Id<Link>,List<InOutTime>> inputFlows = new TreeMap<>();
	private static Map<Id<Link>,List<InOutTime>> outputFlows = new TreeMap<>();
	private static Map<Id<Link>,List<TravelTime>> linksTravelTime = new TreeMap<>();

	@Inject
	TrafficDataContainerDefaultImpl (Network network){
		this.network = network;
		for(Link l: network.getLinks().values()) {
			inputFlows.put(l.getId(), new ArrayList());
			outputFlows.put(l.getId(), new ArrayList());
		}
	}
	
	public Map<Id<Link>,List<InOutTime>> getInputFlows(){
		return this.inputFlows;
	}
	
	public Map<Id<Link>,List<InOutTime>> getOutputFlows(){
		return this.outputFlows;
	}
	
	public Map<Id<Link>,List<TravelTime>> getLinksTravelTime(){
		return this.linksTravelTime;
	}
	
	public void linksTravelTimeComputation() {
		List<List<InOutTime>> in =  new ArrayList<>(inputFlows.values());
		List<List<InOutTime>> out = new ArrayList<>(outputFlows.values());
		List<List<TravelTime>> res = new ArrayList();
		
		for(int i = 0;i< out.size();i++ ) {
			List<TravelTime> traveltime = new ArrayList();
			int outCounter= 0;
			for(int inCounter = 0;inCounter<in.get(i).size();inCounter++) {
				for(int newOutCounter = outCounter;newOutCounter < out.get(i).size();newOutCounter++) {
					if(in.get(i).get(inCounter).getId().equals(out.get(i).get(newOutCounter).getId())) {
						int tt = out.get(i).get(newOutCounter).getTime() - in.get(i).get(inCounter).getTime();
						if(tt>0) {
							if(out.get(i).get(newOutCounter).getType()) {
								traveltime.add(new TravelTime(in.get(i).get(inCounter).getTime(),tt));
								newOutCounter++;
								outCounter = newOutCounter;
								break;
							}
							else {
								break;
							}
						}
					}
				}
			}
			res.add(traveltime);	
		}
		int i = 0;
		for(Id<Link> id: inputFlows.keySet()) {
			linksTravelTime.put(id, res.get(i));
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
	
	public void printLinkTravelTime(Id<Link> id) {
		List<TravelTime> ttl = linksTravelTime.get(id);
		System.out.println(id.toString());
		for(TravelTime t: ttl) {
			System.out.println(t.getStartTime() + " - "+t.getTravelTime());
		}
	}
}
