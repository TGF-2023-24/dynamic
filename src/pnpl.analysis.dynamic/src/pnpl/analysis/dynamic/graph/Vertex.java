package pnpl.analysis.dynamic.graph;

import PetriNets.PetriNet;
import PetriNets.Place;

public class Vertex {
	VertexLabels label;
	
	Vertex(){}
	
	Vertex(VertexLabels label) {
		this.label = label;
	}
	
	Vertex(PetriNet pn){
		for (Place place : pn.getPlaces()) {
			this.label.addLabel(place.getName(), place.getMarking());
		}
	}
	
	void addLabel(String name, int marking) {
		this.label.addLabel(name, marking);
	}
	
	void addLabel(Place place) {
		this.label.addLabel(place.getName(), place.getMarking());
	}
	
	int size() {
		return this.label.size();
	}
	
	VertexLabels getLabel() {
		return this.label;
	}
	
	public boolean equals(Vertex other) {
		return this.label.equals(other.label) ? true : false;
	}
	
	public String toString() {
		return this.label.toString();
	}
}
