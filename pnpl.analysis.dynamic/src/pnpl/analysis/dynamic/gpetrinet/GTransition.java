package pnpl.analysis.dynamic.gpetrinet;

import java.util.ArrayList;
import java.util.List;

import PetriNets.PTArc;
import PetriNets.TPArc;
import PetriNets.Transition;

//Derived from PetriNets
public class GTransition {

	private String name;
	protected List<GPTArc> inputs = null; //Must be initialized
	protected List<GTPArc> outputs = null; //Must be initialized
	private int delay;
	
	GTransition(Transition trans){
		this.name = trans.getName();
		this.delay = trans.getDelay();
	}
	
	GTransition(GTransition trans){
		this.name = trans.getName();
		this.delay = trans.getDelay();
	}
	
	GTransition(){
		this.name = null;
	}
	
	public boolean equals(GTransition other) {
		if (this.name.equals(other.getName())) {
			if (this.delay == other.getDelay()) {
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
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public int getDelay() {
		return this.delay;
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
