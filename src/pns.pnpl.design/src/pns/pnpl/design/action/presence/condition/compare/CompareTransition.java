package pns.pnpl.design.action.presence.condition.compare;

import PetriNets.PTArc;
import PetriNets.TPArc;
import PetriNets.Transition;

public class CompareTransition implements ComparePT {
	
	private Transition transition;
	
	public CompareTransition(Transition transition) {
		this.transition = transition;
	}

	@Override
	public boolean comparePTArc(PTArc ptArc) {
		if (ptArc.getOutput().equals(this.transition))
			return true;
		return false;
	}

	@Override
	public boolean compareTPArc(TPArc ptArc) {
		if (ptArc.getInput().equals(this.transition))
			return true;
		return false;
	}

}
