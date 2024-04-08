package pnpl.analysis.dynamic.gpetrinet;

import java.util.ArrayList;
import java.util.List;

import PetriNets.PTArc;
import PetriNets.Place;
import PetriNets.TPArc;

//Derived from PetriNets
public class GPlace {
	
	String name;
	int marking;
	List<GTPArc> inputs = null; //Must be initialized
	List<GPTArc> outputs = null; //Must be initialized
	
	GPlace(Place place){
		this.name = place.getName();
		this.marking = place.getMarking();
	}
	
	GPlace(GPlace place){
		this.name = place.getName();
		this.marking = place.getMarking();
	}
	
	public boolean equals(GPlace other) {
		if (this.name.equals(other.getName()))
			if (this.marking == other.getMarking())
				if (this.inputs.equals(other.inputs)) 
					if (this.outputs.equals(other.outputs))
					return true;
			
		return false;
		//return (this.name.equals(other.getName()) && this.marking == other.getMarking() && this.inputs == other.getInputs() && this.outputs == other.getOutputs());
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getMarking() {
		return this.marking;
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
	
	public void setMarking(int marking) {
		this.marking = marking;
	}
	
	public void setInputs(List<GTPArc> inputs){
		this.inputs = inputs;
	}
	
	public void setOutputs(List<GPTArc> outputs){
		this.outputs = outputs;
	}
	
	public String getLabel() {
		return "[" + this.name + ", " + this.marking + "]";
	}
	
	public String toString() {
		String rtr = "";
		
		rtr += this.name + " " + this.marking + "\n";
		
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
