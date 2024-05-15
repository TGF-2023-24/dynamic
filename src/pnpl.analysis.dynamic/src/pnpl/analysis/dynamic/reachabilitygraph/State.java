package pnpl.analysis.dynamic.reachabilitygraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PetriNets.PetriNet;
import PetriNets.Place;
import pnpl.analysis.dynamic.gpetrinet.GPetriNet;
import pnpl.analysis.dynamic.gpetrinet.GPlace;
import pnpl.analysis.dynamic.gpetrinet.GToken;

public class State {

	private Map<String, List<GToken>> state = new HashMap<String, List<GToken>>();
	private int current_time;
	
	public State(PetriNet pn){
		
		for (Place place : pn.getPlaces()) {
			if (place.getMarking() > 0) {
				
				List<GToken> tokens = new ArrayList<GToken>();
				
				//Build an ArrayList for the state
				for (int i = 0; i < place.getMarking(); i++) {
					tokens.add(new GToken());
				}
				
				//Adds the place to the map
				state.put(place.getName(), tokens);
			}
		}
		
		this.current_time = -1;
		
	}
	
	public State (GPetriNet gpn) {
		
		for (GPlace place : gpn.getPlaces()) {
			if (place.getMarking() > 0) {
				
				List<GToken> tokens = new ArrayList<GToken>();
				
				//Build an ArrayList for the state
				for (GToken token : place.getTokens()) {
					GToken new_token = new GToken();
					new_token.setTimestamp(token.getTimestamp());
					tokens.add(new_token);
				}
				
				//Adds the place to the map
				state.put(place.getName(), tokens);
			}
		}
		
		//for timed reachability graphs
		if (gpn.getTime() != -1) {
			this.current_time = gpn.getTime();
		}
		else {
			this.current_time = -1;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		
		if (this.state.keySet().size() != other.state.keySet().size())
			return false;
		
		for (String key : this.state.keySet()) {
			if (!other.state.containsKey(key)) {
				return false;
			}
			
			if (this.state.get(key).size() != other.state.get(key).size()) {
				return false;
			}
			
			//Check each individual element
			for (GToken token : this.state.get(key)) {
				if (!other.state.get(key).contains(token)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public int getTime() {
		return this.current_time;
	}
	
	public void setTime(int time) {
		this.current_time = time;
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    int stateHash = 1;
	    
	    for (String key : this.state.keySet()) {
	    	stateHash *= key.hashCode();
	    }
	    
	    result = prime * result + ((state == null) ? 0 : stateHash);
	    result = prime * result + current_time;
	    return result;
	}
	
	public boolean placeIsIn(GPlace place) {
		if (this.state.containsKey(place.getName()))
			return true;
		return false;
	}
	
	public List<GToken> getTokensPlace(GPlace place) {
		if (this.state.containsKey(place.getName()))
			return this.state.get(place.getName());
		return null;
	}
	
	public String toString() {
		String rtr = "";
		
		if (this.current_time != -1) {
			rtr += current_time + "-";
		}
		
		rtr += "{";
		
		for (String key : this.state.keySet()) {
			rtr += "[" + key + ", ";
			
			//For timed
			if ( this.current_time != -1) {
				for (GToken token : this.state.get(key)) {
					rtr += token.toString() + ", ";
				}
				
				//delete the last ", "
				rtr = rtr.substring(0, rtr.length() - 2) + "], ";
			}
			//For non-timed
			else {
				rtr += this.state.get(key).size() + "], ";
			}
						
		}
		
		//delete the last ", "
		rtr = rtr.substring(0, rtr.length() - 2) + "}";
		
		return rtr;
	}
}
