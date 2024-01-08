package pns.pnpl.design.action.presence.condition.compare;
import PetriNets.PTArc;
import PetriNets.TPArc;

public interface ComparePT {
	boolean comparePTArc(PTArc ptArc);
	boolean compareTPArc(TPArc ptArc);
}
