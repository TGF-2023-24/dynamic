package pnpl.analysis.dynamic.gpetrinet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import PetriNets.PetriNet;
import java.util.PriorityQueue;
import java.util.Comparator;

//Graph where the nodes are the states of the PetriNet and the edges the transition fired to reach those states
public class Graph {
	private Map<String, List<Edge>> graph = new HashMap<String, List<Edge>>();
	private String initialState;
	private int time_limit;
	
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
	
	boolean removeVertex(GPetriNet gpn) {
		if (this.graph.remove(gpn.getLabel()) == null) {
			//return false; //there is no element in the graph!
		}
		
		//Removes all edges associated with the state removed
		for (List<Edge> edges : this.graph.values()) {
			for (Edge edge : edges) {
				if (edge.getOutput().equals(gpn) || edge.getInput().equals(gpn)) {
					edges.remove(edge);
					return true;
				}
			}
		}
		
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
	
	public void timedReachabilityGraph(GPetriNet gpn, int time_limit) {
		
		//Set the timed reachability graph
		gpn.updateTime(0);
		
		//Original Petri Net added as the first element
		this.graph.put(gpn.getLabel(), new ArrayList<Edge>());
		this.initialState = gpn.getLabel();
		
		this.time_limit = time_limit;

		try {
			//Build the reachability graph recursively
			this.recursiveTimeReachabilityGraph(gpn);
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
	
	boolean transitionTimeFire(GTransition trans, boolean fire, int global_time) {
		
		for (GPTArc arc : trans.getInputs()) {
			if (arc.getInput().getTokens().isEmpty() || arc.getInput().getTokens().size() < arc.weight || transitionOnTime(trans, arc.getInput(), global_time) )
				return false;
		}
		
		if (fire == true) {
		
			for (GPTArc arc : trans.getInputs()) {
				//Deletes a number of tokens equals to the arc weight
				for (int i = 0; i < arc.weight; i++) {
					arc.getInput().deleteToken();
				}
			}
			
			for(GTPArc arc : trans.getOutputs()) {
				//Creates a number of tokens equals to the arc weight
				for (int i = 0; i < arc.weight; i++) {
					arc.getOutput().addToken(new GToken(global_time + trans.getDelay()));
				}
			}
		}
		
		return true;
	}
	
	boolean transitionOnTime(GTransition trans, GPlace place, int global_time){
		for (GToken token : place.getTokens()) {
			if (token.getTimestamp() + trans.getDelay() > global_time) {
				return false;
			}
		}
		return true;
	}
	
	void recursiveReachabilityGraph(GPetriNet gpn) {
		
		//For each transition on the Petri net
		for (int trans_ind = 0; trans_ind < gpn.getTrans().size(); trans_ind++) {
			GTransition trans = gpn.getTrans().get(trans_ind);
			
			//If the transition could be fired, we add it (or update) the graph
			if (this.transitionFire(trans, false)) {
				GPetriNet nGpn = new GPetriNet(gpn);
				this.transitionFire(nGpn.getTrans().get(trans_ind), true);
				if(this.AddVertexTrans(gpn, nGpn, nGpn.getTrans().get(trans_ind))) {
					this.recursiveReachabilityGraph(nGpn);
				}
			}
		}
	}
	
	void recursiveTimeReachabilityGraph(GPetriNet gpn) {
		
		//To compare the transitions
		Comparator<GTransition> delay_comparator = new Comparator<GTransition>() {
			@Override
			public int compare(GTransition one, GTransition another) {
				
				
				//If both delays are not the same
				//Returns:the value 0 if x == y;a value less than 0 if x < y; and a value greater than 0 if x > y
				if (Integer.compare(one.getDelay(), another.getDelay()) != 0) {
					return Integer.compare(one.getDelay(), another.getDelay());
				}
				//If the delays are the same
				else {
					int earliest_timestamp_one = Integer.MAX_VALUE;
					
					for (GPTArc arcs : one.getInputs()) {
						GPlace from_place = arcs.getInput();
						
						for (GToken token : from_place.getTokens()) {
							if (token.getTimestamp() < earliest_timestamp_one) {
								earliest_timestamp_one = token.getTimestamp();
							}
						}
					}
					
					for (GPTArc arcs : another.getInputs()) {
						GPlace from_place = arcs.getInput();
						
						for (GToken token : from_place.getTokens()) {
							if (token.getTimestamp() < earliest_timestamp_one) {
								return -1; //another is smaller than one
							}
						}
					}
					
					return 1; //one is smaller than another
				}
				
				
			}
		};
		
		//To search for the first transition that could fire
		PriorityQueue<GTransition> transitions = new PriorityQueue<>(delay_comparator);
		
		//Add all the transitions on the Petri net by time
		for (int trans_ind = 0; trans_ind < gpn.getTrans().size(); trans_ind++) {
			GTransition trans = gpn.getTrans().get(trans_ind);
			
			transitions.offer(trans);
		}
		
		for (GTransition my_trans : transitions) {
			if (this.transitionTimeFire(my_trans, false, gpn.getTime())) {
				GPetriNet nGpn = new GPetriNet(gpn);
				int trans_ind = nGpn.getTransitionIndex(my_trans);
				
				this.transitionTimeFire(nGpn.getTrans().get(trans_ind), true, nGpn.getTime());
				if(this.AddVertexTrans(gpn, nGpn, nGpn.getTrans().get(trans_ind))) {
					
					if (nGpn.getTime() + nGpn.getTrans().get(trans_ind).getDelay() > this.time_limit) {
						//Reached the time limit!
						//remove the last vertex (Its timestamp is greater than the time limit)
						if (this.removeVertex(nGpn) == false)
						{
							System.out.println("Did not removed the last state!!!");
						}
						return;
					}
					
					//Do not update the token time
					//nGpn.updateTime(nGpn.getTime() + nGpn.getTrans().get(trans_ind).getDelay());
					
					//Update the Petri net time
					nGpn.addTime(nGpn.getTrans().get(trans_ind).getDelay());
					
					this.recursiveTimeReachabilityGraph(nGpn);
					
					return; //We only explore the first fired transition
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
	
		String rtr = "";
		public String toTimedGraphviz() {
		
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
