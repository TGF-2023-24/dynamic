package pnpl.analysis.dynamic.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import PetriNets.PTArc;
import PetriNets.PetriNet;
import PetriNets.Place;
import PetriNets.TPArc;
import PetriNets.Transition;

public class Graph {
	private Map<Vertex, List<Edge>> graph;
	
	Graph(){
		//
	}
	
	boolean AddVertex(EList<Place> places) {
		
		Vertex newVertex = new Vertex();
		for (Place place : places) {
			newVertex.addLabel(place.getName(), place.getMarking());
		}
		
		if (this.graph.containsKey(newVertex)) {
			return false;
		}
		else {
			this.graph.put(newVertex, new ArrayList<Edge>());
			return true;
		}
	}
	
	boolean AddVertex(PetriNet pn) {
		return this.AddVertex(pn.getPlaces());
	}
	
	boolean AddVertex(Vertex ver) {
		this.graph.put(ver, new ArrayList<Edge>());
		return true;
	}
	
	boolean AddVertexEdge(EList<Place> places, Vertex from, Transition edge) {
		
		//Creamos el nuevo vertice
		Vertex newVertex = new Vertex();
		for (Place place : places) {
			newVertex.addLabel(place.getName(), place.getMarking());
		}
		
		//Si el vertice origen no esta, error
		if (!this.graph.containsKey(from)) {
			//Error!
			return false;
		}
		
		List<Edge> list = this.graph.get(from);
		//Si el grafo ya contiene el nuevo vertice actualizamos no hacemos nada
		if (this.graph.containsKey(newVertex)) {
			if (list.contains(new Edge(edge.getName(), newVertex))) {
				//Estamos dando vueltas, ya hemos recorrido esto
				return false;
			}
		}
		//Si el grafo no tiene el vertice generado lo creamos
		else {
			this.AddVertex(newVertex);
		}
		//A�adimos la nueva arista a la lista del vertice origen
		list.add(new Edge(edge.getName(), newVertex));
		this.graph.put(from, list);
		
		return true;
	}
	
boolean AddVertexVertexEdge(Vertex to, Vertex from, Transition edge) {
		
		//Creamos el nuevo vertice
		Vertex newVertex = new Vertex();
		
		//DELETE TOKEN
		/*
		for (Place place : places) {
			newVertex.addLabel(place.getName(), place.getMarking());
		}
		*/
		
		//Si el vertice origen no esta, error
		if (!this.graph.containsKey(from)) {
			//Error!
			return false;
		}
		
		List<Edge> list = this.graph.get(from);
		//Si el grafo ya contiene el nuevo vertice actualizamos no hacemos nada
		if (this.graph.containsKey(newVertex)) {
			if (list.contains(new Edge(edge.getName(), newVertex))) {
				//Estamos dando vueltas, ya hemos recorrido esto
				return false;
			}
		}
		//Si el grafo no tiene el vertice generado lo creamos
		else {
			this.AddVertex(newVertex);
		}
		//A�adimos la nueva arista a la lista del vertice origen
		list.add(new Edge(edge.getName(), newVertex));
		this.graph.put(from, list);
		
		return true;
	}

	boolean AddVertexTrans(PetriNet from, PetriNet to, Transition edge) {
		
		Vertex fromVertex = new Vertex(from);
		Vertex toVertex = new Vertex(to);
		Edge edgeGraph = new Edge(edge.getName(), toVertex);
		
		List<Edge> list = this.graph.get(fromVertex);
		if (list.contains(edgeGraph)) {
			//Estamos dando vueltas
			return false;
		}
		list.add(edgeGraph);
		this.graph.put(fromVertex, list);
		
		return true;
	}
	
	boolean AddEdge(Vertex from, Vertex to, Transition edge) {
		
		//Si el grafo contiene ambos vertices a�adimos una arista del origen al destino
		if (this.graph.containsKey(from)) {
			if (this.graph.containsKey(to)) {
				List<Edge> list = this.graph.get(from);
				Edge newEdge = new Edge(edge.getName(), to);
				if (list.contains(newEdge)) {
					//Estamos dando vueltas
					return false;
				}
				else {
					list.add(newEdge);
					this.graph.put(from, list);
					return true;
				}
			}
			else {
				//No se encuentra el vertice to
				return false;
			}
		}
		else {
			//No se encuentra el vertice from
			return false;
		}
	}
	
	void reachabilityGraph(PetriNet pn) {
		
		EList<Place> places = pn.getPlaces();
		Vertex newVertex = new Vertex();
		
		//We search first for the initial places
		for (Place place : places) {
			newVertex.addLabel(place);
		}
		
		if (newVertex.size() == 0) {
			//No hay ningun lugar con tokens
			return;
		}
		
		this.AddVertex(newVertex);
		this.recursiveReachabilityGraph(pn);
		
		System.out.println(this.toString());
		
		return;
		
	}
	
	boolean transitionCouldFire(Transition transition) {
		
		EList<PTArc> inputs = transition.getInputs();		
		for (PTArc arc : inputs) {
			if (arc.getInput().getMarking() == 0) return false;
		}
		
		return true;
	}
	
	boolean fireTransition(Transition transition) {
		EList<PTArc> inputs = transition.getInputs();		
		for (PTArc arc : inputs) {
			if (arc.getInput().getMarking() > 0) {
				arc.getInput().setMarking(arc.getInput().getMarking() - 1);
			}
			else {
				return false;
			}
		}
		
		EList<TPArc> outputs = transition.getOutputs();
		for (TPArc arc : outputs) {
			arc.getOutput().setMarking(arc.getOutput().getMarking() + 1);
		}
		
		return true;
	}
	
	void recursiveReachabilityGraph(PetriNet pn) {
		
		//Recorremos todos las transiciones de la red
		for (int trans_ind = 0; trans_ind < pn.getTrans().size(); trans_ind++) {
			Transition trans = pn.getTrans().get(trans_ind);
			
			//Cambiar
		for (int place_ind = 0; place_ind < pn.getPlaces().size(); place_ind++) {
			Place place = pn.getPlaces().get(place_ind);
			
			if (this.transitionCouldFire(trans)) {
				PetriNet newPn = pn;
				Transition newTrans = newPn.getTrans().get(trans_ind);
				if (this.fireTransition(newTrans)) {
					//DELETE TOKEN
					/*
					this.AddVertex(pn, newPn, newTrans);
					*/
					this.recursiveReachabilityGraph(newPn);
				}
			}
			
			/*
			//Por cada lugar con al menos un token
			if (place.getMarking() > 0) {
				
				//Recorremos todos sus arcos salientes
				for (int edge_ind = 0; edge_ind < place.getOutputs().size(); edge_ind++) {
					PTArc edge = place.getOutputs().get(edge_ind);
					
					//Si la transicion solo tiene una llegada la podemos disparar
					Transition tran = edge.getOutput();
					if (tran.getInputs().size() == 1) {
						PetriNet newPn = pn;
						newPn.getPlaces().get(place_ind).setMarking(newPn.getPlaces().get(place_ind).getMarking());
						
						//Creamos un token en cada lugar de llegada de la transicion
						for (int output_tran = 0; output_tran < tran.getOutputs().size(); output_tran++) {
							TPArc edge_to = tran.getOutputs().get(output_tran);
							Place place_to = edge_to.getOutput();
							place_to.setMarking(place_to.getMarking() + 1);
						}
					}
					for (TPArc edge2 : edge.getOutput().getOutputs()) {
						edge2.getOutput();
					}
					edge.getOutput().getOutputs().
				}
			}
			*/
		}}
		
	}
	
	List<String> getVertexNames(){
		List<String> rtr = new ArrayList<String>();
		for(Vertex ver : this.graph.keySet()) {
			rtr.add(ver.getLabel().toString());
		}
		
		return rtr;
	}
	
	List<String> getPlacesNames(){
		List<String> rtr = new ArrayList<String>();
		
		for (String vertexNames : this.getVertexNames()) {
			vertexNames = vertexNames.replace("[", "");
			vertexNames.replace("]", "");
			vertexNames.replace(" ", "");
			String[] places = vertexNames.split(",");
			for(String pl : places) {
				rtr.add(pl);
			}
		}
		
		return rtr;
	}
	
	public String toString() {
		String str = "";
		
		for (Vertex ver : this.graph.keySet()) {
			for (Edge ed : this.graph.get(ver)) {
				str += ver.toString() + ed.toString() + "\n";
			}
			str += "\n";
		}
		
		return str;
	}
}
