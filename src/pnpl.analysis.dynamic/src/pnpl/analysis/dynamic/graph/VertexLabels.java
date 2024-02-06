package pnpl.analysis.dynamic.graph;

import java.util.List;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;

public class VertexLabels {
	List<Pair<String, Integer>> label;

	VertexLabels(List<Pair<String, Integer>> label){
		this.label = label;
	}
	
	List<Pair<String, Integer>> getLabel(){
		return this.label;
	}
	
	void addLabel(String label, int marking) {
		this.label.add(Tuples.pair(label, marking));
	}
	
	Pair<String, Integer> getPairAt(int index){
		if (index >= this.label.size())
			return null;
		return this.label.get(index);
	}
	
	int size() {
		return this.label.size();
	}
	
	public boolean equals(VertexLabels other) {
		if (this.label.size() != other.label.size())
			return false;
		
		for (int i = 0; i < this.label.size(); i++) {
			if (!this.label.get(i).equals(other.getPairAt(i)))
				return false;
		}
		
		return true;
	}
	
	public String toString() {
		String rtr = "{";
		
		for (Pair<String, Integer> pair : this.label) {
			rtr += "[" + pair.getOne() + "," + pair.getTwo() + "],";
		}
		//Remove the last ","
		rtr = rtr.substring(0, rtr.length());
		rtr += "}";
		return rtr;
	}
	
}
