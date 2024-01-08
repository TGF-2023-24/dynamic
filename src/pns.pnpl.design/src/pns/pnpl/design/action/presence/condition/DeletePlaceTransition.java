package pns.pnpl.design.action.presence.condition;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import PetriNets.Arc;
import PetriNets.PTArc;
import PetriNets.PetriNet;
import PetriNets.Place;
import PetriNets.TPArc;
import PetriNets.Transition;
import pns.pnpl.design.action.presence.condition.compare.ComparePT;
import pns.pnpl.design.action.presence.condition.compare.ComparePlace;
import pns.pnpl.design.action.presence.condition.compare.CompareTransition;

public class DeletePlaceTransition implements IExternalJavaAction {

	public DeletePlaceTransition() {
		// Do nothing
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		if (selections.isEmpty() == false) {
			EObject selectedElement = selections.iterator().next();
			EObject rootElement = selectedElement.eContainer();
			ComparePT compare = null;
			if (selectedElement instanceof Place) {
				compare = new ComparePlace((Place) selectedElement);
			} else if (selectedElement instanceof Transition) {
				compare = new CompareTransition((Transition) selectedElement);
			}
			if (compare == null)
				return;			
			if (rootElement instanceof PetriNet) {
				PetriNet pn = (PetriNet) rootElement;
				Iterator<Arc> iterateArcs = pn.getArcs().iterator();		
				while (iterateArcs.hasNext()) {
					Arc arc = iterateArcs.next();
					boolean foundReference = false;
					if (arc instanceof PTArc) {
						foundReference = compare.comparePTArc((PTArc) arc);
					} else if (arc instanceof TPArc) {
						foundReference = compare.compareTPArc((TPArc) arc);
					}
					if (foundReference == true) {
						iterateArcs.remove();
						EcoreUtil.remove(arc);
					}
				}				
			}
			EcoreUtil.remove(selectedElement);
		}
	}
	
	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
