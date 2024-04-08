package pnpl.analysis.dynamic.gpetrinet;

import PetriNets.PTArc;

//Derived from PetriNets
public class GPTArc extends GArc{
	
	GTransition to;
	GPlace from;
	
	GPTArc(PTArc arc){
		super(arc.getName(), arc.getWeight());
		this.to = null;
	}
	
	GPTArc(PTArc arc, GTransition to, GPlace from){
		super(arc.getName(), arc.getWeight());
		this.to = to;
		this.from = from;
	}
	
	GPTArc(GPTArc arc, GTransition to, GPlace from){
		super(arc.getName(), arc.getWeight());
		this.to = to;
		this.from = from;
	}
	
	public boolean equals(GPTArc other) {
		if (this.name.equals(other.getName()))
			if (this.to.equals(other.getOutput()))
				if (this.from.equals(other.getInput()))
					return true;
		return false;
	}
	
	public GTransition getOutput() {
		return this.to;
	}
	
	public GPlace getInput() {
		return this.from;
	}
	
	public void setOutput(GTransition to) {
		this.to = to;
	}
	
	public void setInput(GPlace from) {
		this.from = from;
	}
	
	public String toString() {
		return this.name + this.to.getName();
	}
	
}
