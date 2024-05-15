package pnpl.analysis.dynamic.reachabilitygraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import PetriNets.PetriNet;
import pnpl.analysis.dynamic.gpetrinet.GPTArc;
import pnpl.analysis.dynamic.gpetrinet.GPetriNet;
import pnpl.analysis.dynamic.gpetrinet.GPlace;
import pnpl.analysis.dynamic.gpetrinet.GTPArc;
import pnpl.analysis.dynamic.gpetrinet.GToken;
import pnpl.analysis.dynamic.gpetrinet.GTransition;

import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.Comparator;

//Graph where the nodes are the states of the PetriNet and the edges the transition fired to reach those states
public class ReachabilityGraph {
	private Map<State, List<Edge>> graph = new HashMap<>();
	private State initialState;
	private int time_limit;
	private GPetriNet petriNet;
	
	public ReachabilityGraph(){
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

	boolean AddVertexTrans(State from, State to, GTransition trans) {
		
		if (from.equals(to)) {
			//There is a transition from and to the same state with equal weight
			return false;
		}
		
		Edge edge = new Edge(from, trans, to);
		
		
		List<Edge> list = this.graph.get(from);
		
		//from does not have any outputs
		if (list == null) {
			list = new ArrayList<Edge>();
		}
		else {
			if (list.contains(edge)) {
				return false;
			}
			//list.contains is not working
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(edge)) {
					return false;
				}
			}
		}
		
		if (this.graph.containsKey(from)) {
			List<Edge> tmp = this.graph.get(from);
		}
		
		list.add(edge);
		this.graph.put(from, list);
		
		return true;
	}

	boolean removeVertex(State vertex_removed) {
		if (this.graph.remove(vertex_removed) == null) {
			//return false; //there is no element in the graph!
		}
		
		//Removes all edges associated with the state removed
		for (List<Edge> edges : this.graph.values()) {
			for (Edge edge : edges) {
				if (edge.getOutput().equals(vertex_removed) || edge.getInput().equals(vertex_removed)) {
					edges.remove(edge);
					return true;
				}
			}
		}
		
		return true;
	}
	
	public void reachabilityGraph(PetriNet pn) {
		
		//Create the petriNet
		this.petriNet = new GPetriNet(pn);
		this.petriNet.setTime(-1); //Mark as non-timed petri net
		
		//Original Petri Net added as the first element
		State firstState = new State(pn);
		this.graph.put(firstState, new ArrayList<Edge>());
		this.initialState = firstState;

		try {
			//Build the reachability graph recursively
			this.recursiveReachabilityGraph(firstState);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	public void timedReachabilityGraph(PetriNet pn, int time_limit) {
		
		//Create the petriNet
		this.petriNet = new GPetriNet(pn);
		
		//Set the timed reachability graph
		this.petriNet.updateTime(0);
		
		//Original Petri Net added as the first element
		State firstState = new State(this.petriNet);
		this.graph.put(firstState, new ArrayList<Edge>());
		this.initialState = firstState;
		
		this.time_limit = time_limit;

		try {
			//Build the reachability graph recursively
			this.recursiveTimeReachabilityGraph(firstState);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return;
	}
	
	boolean transitionFire(GTransition trans, boolean fire) {
		
		for (GPTArc arc : trans.getInputs()) {
			if (arc.getInput().getMarking() < arc.getWeight())
				return false;
		}
		
		if (fire == true) {
		
			for (GPTArc arc : trans.getInputs()) {
				arc.getInput().setMarking(arc.getInput().getMarking() - arc.getWeight());
			}
			
			for(GTPArc arc : trans.getOutputs()) {
				arc.getOutput().setMarking(arc.getOutput().getMarking() + arc.getWeight());
			}
		}
		
		return true;
	}
	
	boolean transitionTimeFire(GTransition trans, boolean fire, int global_time) {
		
		for (GPTArc arc : trans.getInputs()) {
			if (arc.getInput().getTokens().isEmpty() || arc.getInput().getTokens().size() < arc.getWeight() || transitionOnTime(trans, arc.getInput(), global_time) )
				return false;
		}
		
		if (fire == true) {
		
			for (GPTArc arc : trans.getInputs()) {
				//Deletes a number of tokens equals to the arc weight
				for (int i = 0; i < arc.getWeight(); i++) {
					arc.getInput().deleteLastToken();
				}
			}
			
			for(GTPArc arc : trans.getOutputs()) {
				//Creates a number of tokens equals to the arc weight
				for (int i = 0; i < arc.getWeight(); i++) {
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
	
	void recursiveReachabilityGraph(State og_state) {
		
		this.petriNet.updateState(og_state);
		
		//For each transition on the Petri net
		for (int trans_ind = 0; trans_ind < this.petriNet.getTrans().size(); trans_ind++) {
			GTransition trans = this.petriNet.getTrans().get(trans_ind);
			
			//If the transition could be fired, we add it (or update) the graph
			if (this.transitionFire(trans, true)) {
				State new_state = new State(this.petriNet);

				if(this.AddVertexTrans(og_state, new_state, this.petriNet.getTrans().get(trans_ind))) {
					this.recursiveReachabilityGraph(new_state);
				}
			}
			
			//Updates the state to the original one
			this.petriNet.updateState(og_state);
		}
	}
	
	void recursiveTimeReachabilityGraph(State og_state) {
		
		this.petriNet.updateState(og_state);
		
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
		for (int trans_ind = 0; trans_ind < this.petriNet.getTrans().size(); trans_ind++) {
			GTransition trans = this.petriNet.getTrans().get(trans_ind);
			
			transitions.offer(trans);
		}
		
		for (GTransition my_trans : transitions) {

			if (this.transitionTimeFire(my_trans, true, this.petriNet.getTime())) {
				
				int trans_ind = this.petriNet.getTransitionIndex(my_trans);
				
				if (this.petriNet.getTime() + this.petriNet.getTrans().get(trans_ind).getDelay() > this.time_limit) {
					return;
				}
				
				this.petriNet.setTime(this.petriNet.getTime() + this.petriNet.getTrans().get(trans_ind).getDelay());
				State new_state = new State(this.petriNet);

				if(this.AddVertexTrans(og_state, new_state, this.petriNet.getTrans().get(trans_ind))) {
					//Do not update the token time
					//nGpn.updateTime(nGpn.getTime() + nGpn.getTrans().get(trans_ind).getDelay());

					//Update the Petri net time
					//nGpn.addTime(nGpn.getTrans().get(trans_ind).getDelay());

					System.out.println(this.toTimedGraphviz());
					this.recursiveTimeReachabilityGraph(new_state);

					return; //We only explore the first fired transition
				}
			}
			
			this.petriNet.updateState(og_state);
		}
	}
	
	
	public String toString() {
		String rtr = "";
		
		for (State state : this.graph.keySet()) {
			rtr += state.toString() + "\n";
			for (Edge edge : this.graph.get(state)) {
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
		
		for (State ver : this.graph.keySet()) {		
			for (Edge edge : this.graph.get(ver)) {
				rtr += "\t\"" + ver.toString() + "\" -> \"" + edge.getOutput().toString() + "\" [label = \"" + edge.toGraphviz() + "\"];\n";
			}
		}

		rtr += "}";

		return rtr;
	}

	public String toTimedGraphviz() {
		String rtr = "";

		rtr += GRAPHVIZ_HEADER;
		rtr += " \"" + this.initialState.toString() + "\";\n";
		rtr += GRAPHVIZ_HEADER_2;

		for (State ver : this.graph.keySet()) {		
			for (Edge edge : this.graph.get(ver)) {
				rtr += "\t\"" + ver.toString() + "\" -> \"" + edge.getOutput().toString() + "\" [label = \"" + edge.toGraphviz() + "\"];\n";
			}
		}

		rtr += "}";

		return rtr;
	}
}
