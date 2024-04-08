package pnpl.analysis.dynamic.gpetrinet;

import java.util.ArrayList;
import java.util.List;

import PetriNets.PTArc;
import PetriNets.TPArc;
import PetriNets.Transition;

//Derived from PetriNets
public class GTransition {

	String name;
	List<GPTArc> inputs = null; //Must be initialized
	List<GTPArc> outputs = null; //Must be initialized
	
	GTransition(Transition trans){
		this.name = trans.getName();
	}
	
	GTransition(GTransition trans){
		this.name = trans.getName();
	}
	
	GTransition(){
		this.name = null;
	}
	
	public boolean equals(GTransition other) {
		if (this.name.equals(other.getName())) {
			if (this.inputs.equals(other.inputs)) {
				if (this.outputs.equals(other.outputs))
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<GPTArc> getInputs(){
		return this.inputs;
	}
	
	public List<GTPArc> getOutputs(){
		return this.outputs;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInputs(List<GPTArc> inputs) {
		this.inputs = inputs;
	}
	
	public void setOutputs(List<GTPArc> outputs) {
		this.outputs = outputs;
	}
	
	public String toString() {
		String rtr = "";
		
		rtr += this.name + "\n";
		
		rtr += "[Inputs]:\n";
		for (GPTArc arc : this.inputs) {
			rtr += arc.toString() + "\n";
		}
		
		rtr += "[Outputs]:\n";
		for (GTPArc arc : this.outputs) {
			rtr += "\t" + arc.toString() + "\n";
		}
		
		return rtr;
	}
	
}
