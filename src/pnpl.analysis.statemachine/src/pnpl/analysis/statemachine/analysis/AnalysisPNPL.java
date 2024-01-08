package pnpl.analysis.statemachine.analysis;

import java.util.List;

import com.google.common.collect.Lists;

import PetriNets.PTArc;
import PetriNets.PetriNet;
import PetriNets.TPArc;
import PetriNets.Transition;
import pnpl.analysis.analysis.AbstractAnalysisPNPL;
import pnpl.analysis.helpers.ClauseString;

public class AnalysisPNPL extends AbstractAnalysisPNPL {

	@Override
	protected boolean buildCondition() {
		condition = "";
		PetriNet pn = vh.getPetriNet();
		if (pn == null) return false;

		for(Transition t : pn.getTrans()) {
			String pc = "";
			
			String pc_input = "";
			List<PTArc> inputs = t.getInputs();
			if(inputs != null) {
				if(inputs.size() > 1) {
					List<String> list = Lists.newArrayList();
					for (PTArc a : inputs) {
						String pc_arc = pclist.get(a.getName());	// PC of the arc
						if(ClauseString.isBlank(pc_arc))
							pc_arc = pclist.get(a.getOutput().getName());	// PC of the outgoing transition
						
						if(!ClauseString.isBlank(pc_arc))
							list.add(pc_arc);		
					}
					// Some of the incoming arcs has no PC
					if (inputs.size() - list.size() > 0)						
						return false;
					
					pc_input = ClauseString.disjunctionOfConjunction(list);					
				}				
			}
			
			String pc_output = "";
			List<TPArc> outputs = t.getOutputs();
			if(outputs != null) {
				if(outputs.size() > 1) {
					List<String> list = Lists.newArrayList();
					for (TPArc a : outputs) {
						String pc_arc = pclist.get(a.getName());	// PC of the arc
						if(ClauseString.isBlank(pc_arc))
							pc_arc = pclist.get(a.getInput().getName());	// PC of the outgoing transition
						
						if(!ClauseString.isBlank(pc_arc))
							list.add(pc_arc);
					}
					
					// Some of the outgoing arcs has no PC
					if (outputs.size() - list.size() > 0) 
						return false;
					
					pc_output = ClauseString.disjunctionOfConjunction(list);
				}				
			}

			pc = ClauseString.conjunction(pc_input, pc_output);
						
			if(!ClauseString.isBlank(pc)) {
				String pc_trans = pclist.get(t.getName());
				if (!ClauseString.isBlank(pc_trans)) {
					pc = ClauseString.conjunction(pc_trans, pc);			
					pc = ClauseString.parenthesis(pc);
					pc_trans = ClauseString.negation(pc_trans);
					pc = ClauseString.disjunction(pc_trans, pc);
				}
				pc = ClauseString.parenthesis(pc);
			}
			condition = ClauseString.conjunction(condition, pc);		
		}
		return true;
	}
}
