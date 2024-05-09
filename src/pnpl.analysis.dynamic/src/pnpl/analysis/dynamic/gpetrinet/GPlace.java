package pnpl.analysis.dynamic.gpetrinet;

import java.util.ArrayList;
import java.util.List;

import PetriNets.PTArc;
import PetriNets.Place;
import PetriNets.TPArc;
import PetriNets.TimedPlace;

//Derived from PetriNets
public class GPlace {
	
	String name;
	List<GTPArc> inputs = null; //Must be initialized
	List<GPTArc> outputs = null; //Must be initialized
	List<GToken> tokens = null; //Must be initialized
	
	public GPlace(TimedPlace place) {
		this.name = place.getName();

		this.tokens = new ArrayList<GToken>();
		for (int i = 0; i < place.getMarking(); i++) {
			this.tokens.add(new GToken(place.getTimestamp()));
		}
	}
	
	GPlace(Place place){
		this.name = place.getName();
		
		this.tokens = new ArrayList<GToken>();
		for (int i = 0; i < place.getMarking(); i++) {
			this.tokens.add(new GToken());
		}
	}
	
	GPlace(GPlace place){
		this.name = place.getName();

		this.tokens = new ArrayList<GToken>();
		for (GToken token : place.getTokens()) {
			this.tokens.add(new GToken(token.getTimestamp()));
		}
	}
	
	public boolean equals(GPlace other) {
		if (this.name.equals(other.getName()))
			if (this.tokens.equals(other.getTokens()))
				if (this.inputs.equals(other.inputs)) 
					if (this.outputs.equals(other.outputs))
					return true;
			
		return false;
		//return (this.name.equals(other.getName()) && this.marking == other.getMarking() && this.inputs == other.getInputs() && this.outputs == other.getOutputs());
	}
	
	
	//For the non-timed reachability graph
	public int getMarking() {
		if (this.tokens.isEmpty())
			return 0;
		return this.tokens.size();
	}
	
	public void setMarking(int num) {
		this.tokens = new ArrayList<GToken>();
		
		for (int i = 0; i < num; i++)
		{
			this.addToken(new GToken());
		}
	}

	public void updateTime(int new_time) {
		for (GToken token : this.tokens) {
			token.setTimestamp(new_time);
		}
	}
	
	public List<GToken> getTokens(){
		return this.tokens;
	}
	
	public void setTokens(List<GToken> tokens) {
		this.tokens = tokens;
	}
	
	public void addToken(GToken token) {
		this.tokens.add(token);
	}
	
	public void deleteToken() {
		//removes the last token of the list
		this.tokens.remove(this.tokens.size() - 1);
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<GTPArc> getInputs(){
		return this.inputs;
	}
	
	public List<GPTArc> getOutputs(){
		return this.outputs;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInputs(List<GTPArc> inputs){
		this.inputs = inputs;
	}
	
	public void setOutputs(List<GPTArc> outputs){
		this.outputs = outputs;
	}
	
	public String getLabel() {
		if (this.tokens.isEmpty())
			return "[" + this.name + ", -]";
		else {
			//Not timed
			if (this.tokens.get(0).getTimestamp() == -1) {
				return "[" + this.name + ", " + this.tokens.size() + "]";
			}
			else {
				String rtr = "[" + this.name;
				
				for (GToken token : this.tokens) {
					rtr += ", " + token.getTimestamp();
				}
				
				rtr += "]";
				return rtr;
			}
		}
	}
	
	public String getTimedLabel() {
		if (this.tokens.isEmpty())
			return "[" + this.name + ", -]";
		else {
			//Not timed
			if (this.tokens.get(0).getTimestamp() == -1) {
				return "[" + this.name + ", " + this.tokens.size() + "]";
			}
			else {
				String rtr = "[" + this.name;
				
				for (GToken token : this.tokens) {
					rtr += ", " + token.getTimestamp();
				}
				
				rtr += "]";
				return rtr;
			}
		}
	}
	
	public String toString() {
		String rtr = "";
		
		if (this.tokens.isEmpty())
			rtr += this.name + " 0\n";
		else
			rtr += this.name + " " + this.tokens.size() + "\n";
		
		rtr += "[Inputs]:\n";
		for (GTPArc arc : this.inputs) {
			rtr += arc.toString() + "\n";
		}
		
		rtr += "[Outputs]:\n";
		for (GPTArc arc : this.outputs) {
			rtr += "\t" + arc.toString() + "\n";
		}
		
		return rtr;
	}

}
