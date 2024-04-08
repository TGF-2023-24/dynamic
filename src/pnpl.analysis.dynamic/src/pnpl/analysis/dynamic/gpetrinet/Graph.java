package pnpl.analysis.dynamic.gpetrinet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import PetriNets.PetriNet;

//Graph where the nodes are the states of the PetriNet and the edges the transition fired to reach those states
public class Graph {
	private Map<String, List<Edge>> graph = new HashMap<String, List<Edge>>();
	private String initialState;
	
	public Graph(){
		//
	}
	
	String GRAPHVIZ_HEADER = "digraph reachability_graph {\n" //Poner aqui "reachability_graph" + nombre del proyecto?
			+ "\tfontname=\"Helvetica,Arial,sans-serif\"\n" + 
			"\tnode [fontname=\"Helvetica,Arial,sans-serif\"]\n" + 
			"\tedge [fontname=\"Helvetica,Arial,sans-serif\"]\n" + 
			"\tlayout=neato\r\n" + 
			"\toverlap=false\r\n" + 
			"\tlabel=\"Reachability graph of Petri Net\"" + 
			"\tnode [shape = doublecircle];"; //Falta poner aqui el estado inicial al crear el grafo
	
	String GRAPHVIZ_HEADER_2 ="\tnode [shape = circle];\n";

	boolean AddVertexTrans(GPetriNet from, GPetriNet to, GTransition trans) {
		
		Edge edge = new Edge(from, trans, to);
		List<Edge> list = this.graph.get(from.getLabel());
		
		//from does not have any outputs
		if (list == null) {
			list = new ArrayList<Edge>();
		}
		else {
			//list.contains is not working
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(edge)) {
					return false;
				}
			}
		}
		
		if (this.graph.containsKey(from.getLabel())) {
			List<Edge> tmp = this.graph.get(from.getLabel());
		}
		
		list.add(edge);
		this.graph.put(from.getLabel(), list);
		
		return true;
	}
	
	public void reachabilityGraph(GPetriNet gpn) {
		
		//Original Petri Net added as the first element
		this.graph.put(gpn.getLabel(), new ArrayList<Edge>());
		this.initialState = gpn.getLabel();

		try {
			//Build the reachability graph recursively
			this.recursiveReachabilityGraph(gpn);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return;
		
	}
	
	boolean transitionFire(GTransition trans, boolean fire) {
		
		for (GPTArc arc : trans.getInputs()) {
			if (arc.getInput().getMarking() < arc.weight)
				return false;
		}
		
		if (fire == true) {
		
			for (GPTArc arc : trans.getInputs()) {
				arc.getInput().setMarking(arc.getInput().getMarking() - arc.weight);
			}
			
			for(GTPArc arc : trans.getOutputs()) {
				arc.getOutput().setMarking(arc.getOutput().getMarking() + arc.weight);
			}
		}
		
		return true;
	}
	
	void recursiveReachabilityGraph(GPetriNet gpn) {
		
		//System.out.println("Recursive:\n" + this.toString());
		
		//Recorremos todos las transiciones de la red
		for (int trans_ind = 0; trans_ind < gpn.getTrans().size(); trans_ind++) {
			GTransition trans = gpn.getTrans().get(trans_ind);
			
			if (this.transitionFire(trans, false)) {
				GPetriNet nGpn = new GPetriNet(gpn);
				this.transitionFire(nGpn.getTrans().get(trans_ind), true);
				if(this.AddVertexTrans(gpn, nGpn, nGpn.getTrans().get(trans_ind))) {
					this.recursiveReachabilityGraph(nGpn);
				}
			}
		}
	}
	
	
	public String toString() {
		String rtr = "";
		
		for (String ver : this.graph.keySet()) {
			rtr += ver + "\n";
			for (Edge edge : this.graph.get(ver)) {
				rtr += "\t" + edge.toString() + "\n";
			}
			rtr += "\n";
		}
		
		return rtr;
	}
	
	public String toGraphviz() {
		String rtr = "";
		
		rtr += GRAPHVIZ_HEADER;
		rtr += " \"" + this.initialState + "\";\n";
		rtr += GRAPHVIZ_HEADER_2;
		
		for (String ver : this.graph.keySet()) {		
			for (Edge edge : this.graph.get(ver)) {
				rtr += "\t\"" + ver + "\" -> \"" + edge.getOutput().getLabel() + "\" [label = \"" + edge.toGraphviz() + "\"];\n";
			}
		}
		
		rtr += "}";
		
		return rtr;
	}
}
