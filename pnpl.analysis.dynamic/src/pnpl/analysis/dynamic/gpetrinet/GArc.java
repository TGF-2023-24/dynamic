package pnpl.analysis.dynamic.gpetrinet;

import PetriNets.Arc;
import PetriNets.PTArc;
import PetriNets.TPArc;

//Derived from PetriNets
public class GArc {

	int weight;
	String name;
	
	GArc(String name, int weight){
		this.name = name;
		this.weight = weight;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}	
	
}
