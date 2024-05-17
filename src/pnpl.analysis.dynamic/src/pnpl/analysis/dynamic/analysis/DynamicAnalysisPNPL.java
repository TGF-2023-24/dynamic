package pnpl.analysis.dynamic.analysis;

import org.eclipse.core.resources.IFile;

import PetriNets.PetriNet;
import pnpl.analysis.analysis.AbstractAnalysis;
import pnpl.analysis.dynamic.reachabilitygraph.ReachabilityGraph;

public class DynamicAnalysisPNPL extends AbstractAnalysis {
	
	protected boolean buildCondition() {
		// Nota para Javier de EG: Aqui tendras que poner el codigo para hacer el reachability graph de una PNPL
		// Se que no es evidente, pero viene de un nombre antiguo
		
		System.out.println("[REACHABILITY GRAPH]: Loading PetriNet...");
		PetriNet pn = vh.getPetriNet();
		
		if (pn == null) {
			System.out.println("[REACHABILITY GRAPH]: Could not load the PetriNet!");
			return false;
		}
		
		//Creates the reachability grap from the given PetriNet
		ReachabilityGraph graph = new ReachabilityGraph();
		System.out.println("[REACHABILITY GRAPH]: Building reachability graph...");
		graph.reachabilityGraph(pn);
		//Output for Graphviz
		System.out.println("[REACHABILITY GRAPH]: Reachability graph for Graphviz.");
		String reachability_graph_Graphviz_format = graph.toGraphviz();
		
		System.out.println(reachability_graph_Graphviz_format);
	
		return true;
	}

	@Override
	public boolean run() {
		// TODO Auto-generated method stub
		this.buildCondition();
		return false;
	}
}
