package pnpl.analysis.dynamic.analysis;

import pnpl.analysis.analysis.AbstractAnalysisPNPL;

public class AnalysisPNPL extends AbstractAnalysisPNPL {

	@Override
	protected boolean buildCondition() {
		// Nota para Javier de EG: Aquí tendrías que poner el codigo para hacer el RG de una PNPL
		// Sé que no es evidente, pero viene de un nombre antiguo
		return true;
	}
	
//	@Override
//	protected boolean buildReachabilityGraph() {
//		
//		
//		
//		PetriNet pn = vh.getPetriNet();
//		if (pn == null) return false;
//		
//		
//		return true;
//	}
}
