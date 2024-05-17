package pnpl.analysis.dynamic.analysis;

import org.eclipse.core.resources.IFile;

import PetriNets.PetriNet;
import pnpl.analysis.dynamic.analysis.AnalysisPNPL;
import pnpl.analysis.dynamic.gpetrinet.GPetriNet;
import pnpl.analysis.dynamic.helpers.VariabilityHelper;
import pnpl.analysis.dynamic.reachabilitygraph.ReachabilityGraph;

public class TimedAnalysisPNPL extends AbstractAnalysisDynamic {
	
	protected boolean buildCondition() {
		// Nota para Javier de EG: Aqui tendras que poner el codigo para hacer el reachability graph de una PNPL
		// Se que no es evidente, pero viene de un nombre antiguo
		
		System.out.println("[REACHABILITY GRAPH]: Loading PetriNet...");
		PetriNet pn = vh.getPetriNet();
		
		IFile tmp_file = vh.getPetriNetFile();
		
		//Creates the reachability grap from the given PetriNet
		ReachabilityGraph graph = new ReachabilityGraph();
		System.out.println("[TIMED REACHABILITY GRAPH]: Building reachability graph...");
		
		graph.timedReachabilityGraph(pn, 100);
		//Output for Graphviz
		System.out.println("[TIMED REACHABILITY GRAPH]: Reachability graph for Graphviz.");
		//String reachability_graph_Graphviz_format = graph.toGraphviz();
		String reachability_graph_Graphviz_format = graph.toTimedGraphviz();
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
