package pnpl.analysis.dynamic.gpetrinet;

import PetriNets.TPArc;

//Derived from PetriNets
public class GTPArc extends GArc {

	GPlace to;
	GTransition from;
	
	GTPArc(TPArc arc, GPlace to, GTransition from){
		super(arc.getName(), arc.getWeight());
		this.to = to;
		this.from = from;
	}
	
	GTPArc(GTPArc arc, GPlace to, GTransition from){
		super(arc.getName(), arc.getWeight());
		this.to = to;
		this.from = from;
	}
	
	GTPArc(TPArc arc){
		super(arc.getName(), arc.getWeight());
		this.to = null;
	}
	
	public boolean equals(GTPArc other) {
		if (this.name.equals(other.getName()))
			if (this.to.equals(other.getOutput()))
				if (this.from.equals(other.getInput()))
					return true;
		return false;
	}
	
	public GPlace getOutput() {
		return this.to;
	}
	
	public GTransition getInput() {
		return this.from;
	}
	
	public void setOutput(GPlace to) {
		this.to = to;
	}
	
	public void setInput(GTransition from) {
		this.from = from;
	}
	
	public String toString() {
		return this.name + this.to.getName();
	}
	
}
