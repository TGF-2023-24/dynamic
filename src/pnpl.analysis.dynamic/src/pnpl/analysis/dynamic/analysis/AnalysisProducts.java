package pnpl.analysis.dynamic.analysis;

import java.util.List;

import PetriNets.PTArc;
import PetriNets.TPArc;
import PetriNets.Transition;
import pnpl.analysis.analysis.AbstractAnalysisProducts;

public class AnalysisProducts extends AbstractAnalysisProducts {

	@Override
	protected boolean runProductAnalysis() {
		if (pn == null) return false;

		for(Transition t : pn.getTrans()) {
			List<PTArc> inputs = t.getInputs();
			if(inputs != null) {
				if(inputs.size() > 1) {
					return false;
				}				
			}

			List<TPArc> outputs = t.getOutputs();
			if(outputs != null) {
				if(outputs.size() > 1) {
					return false;
				}				
			}
		}
		return true;
	}
}
