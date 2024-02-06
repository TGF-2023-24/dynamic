package pnpl.analysis.dynamic.graph;

public class Edge {

	String label;
	Vertex to;
	
	Edge(String label, Vertex to){
		this.label = label;
		this.to = to;
	}
	
	String getLabel() {
		return this.label;
	}
	
	Vertex getTo() {
		return this.to;
	}
	
	public boolean equals(Edge other) {
		return (this.label.equals(other.getLabel()) && this.to.equals(other.getTo())) ? true : false;
	}
	
	public String toString() {
		return " -> " + this.label + " -> " + this.to.toString();
	}
	
}
