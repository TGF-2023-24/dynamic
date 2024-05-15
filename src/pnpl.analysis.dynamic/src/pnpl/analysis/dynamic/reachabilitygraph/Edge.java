package pnpl.analysis.dynamic.reachabilitygraph;

import pnpl.analysis.dynamic.gpetrinet.GPetriNet;
import pnpl.analysis.dynamic.gpetrinet.GTransition;

//From a PetriNet state to another
public class Edge {

	State from;
	String name; //Transition name fired
	State to;
	
	Edge(State from, GTransition trans, State to){
		this.from = from;
		this.name = trans.getName();
		this.to = to;
	}
	
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		
		if (this.name.equals(other.getName()))
			if (this.from.equals(other.from))
				if (this.to.equals(other.to))
					return true;
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public State getInput() {
		return this.from;
	}
	
	public State getOutput() {
		return this.to;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInput(State input) {
		this.from = input;
	}
	
	public void setOutput(State output) {
		this.to = output;
	}
	
	public String toString() {
		return " -> " + this.name + " -> " + this.to.toString();
	}
	
	public String toGraphviz() {
		return this.name;
	}
	
}
