package pnpl.analysis.dynamic.gpetrinet;

//From a PetriNet state to another
public class Edge {

	GPetriNet from;
	String name; //Transition name fired
	GPetriNet to;
	
	Edge(GPetriNet from, GTransition trans, GPetriNet to){
		this.from = from;
		this.name = trans.getName();
		this.to = to;
	}
	
	public boolean equals(Edge other) {
		if (this.name.equals(other.getName()))
			if (this.from.getLabel().equals(other.getInput().getLabel()))
				if (this.to.getLabel().equals(other.getOutput().getLabel()))
					return true;
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public GPetriNet getInput() {
		return this.from;
	}
	
	public GPetriNet getOutput() {
		return this.to;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInput(GPetriNet input) {
		this.from = input;
	}
	
	public void setOutput(GPetriNet output) {
		this.to = output;
	}
	
	public String toString() {
		return " -> " + this.name + " -> " + this.to.getLabel();
	}
	
	public String toGraphviz() {
		return this.name;
	}
	
}
