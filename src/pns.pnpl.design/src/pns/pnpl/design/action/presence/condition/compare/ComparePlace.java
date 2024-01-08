package pns.pnpl.design.action.presence.condition.compare;

import PetriNets.PTArc;
import PetriNets.TPArc;
import PetriNets.Place;

public class ComparePlace implements ComparePT {

	private Place place;
	
	public ComparePlace(Place place) {
		this.place = place;
	}

	@Override
	public boolean comparePTArc(PTArc ptArc) {
		if (ptArc.getInput().equals(this.place))
			return true;
		return false;
	}

	@Override
	public boolean compareTPArc(TPArc ptArc) {
		if (ptArc.getOutput().equals(this.place))
			return true;		
		return false;
	}

}
