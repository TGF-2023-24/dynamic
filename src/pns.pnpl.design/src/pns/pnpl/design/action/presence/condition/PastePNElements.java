package pns.pnpl.design.action.presence.condition;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import PetriNets.Arc;
import PetriNets.PTArc;
import PetriNets.Place;
import PetriNets.TPArc;
import PetriNets.Transition;
import variability.PresenceCondition;
import variability.Variability;

public class PastePNElements implements IExternalJavaAction {
	
	public static String add2Name = "_1";
	
	public PastePNElements() {
		// Do nothing
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		Object copiedObject = parameters.get("copiedElement");
		Object containerViewObject= parameters.get("containerView");
		Object copiedViewObject = parameters.get("copiedView");		
		EObject eObjectContainer = selections.iterator().next();
		if (copiedObject instanceof EObject && eObjectContainer != null) {
			EObject eObjectElement = (EObject) copiedObject;
			if (eObjectElement instanceof Place) {
				Place place = (Place) eObjectElement;
				addElement(containerViewObject, copiedViewObject);
				addPlace(eObjectContainer, place);			
			} else if (eObjectElement instanceof Transition) {
				Transition trans = (Transition) eObjectElement;
				addElement(containerViewObject, copiedViewObject);
				addTransition(eObjectContainer, trans);
			} else if (eObjectElement instanceof PTArc) {
				PTArc ptArc = (PTArc) eObjectElement;
				if (ptArc.getInput() != null && ptArc.getOutput() != null) {
					addElement(containerViewObject, copiedViewObject);
					addArc(eObjectContainer, ptArc);
				} 
			} else if (eObjectElement instanceof TPArc) {
				TPArc tpArc = (TPArc) eObjectElement;
				if (tpArc.getInput() != null && tpArc.getOutput() != null) {
					addElement(containerViewObject, copiedViewObject);
					addArc(eObjectContainer, tpArc);
				}
			} else if (eObjectElement instanceof PresenceCondition) {
				PresenceCondition presenceCondition = (PresenceCondition) eObjectElement;
				if (presenceCondition.getElements().size() > 0) {
					pasteVariability(presenceCondition, (EObject) containerViewObject);
					addElement(containerViewObject, copiedViewObject);
				}				
			}			
		}		
	}
	
	private void pasteVariability(PresenceCondition presenceCondition, EObject containerViewObject) {				
			Session currentSession = SessionManager.INSTANCE.getSession(containerViewObject);
			Variability variability = getCreateVariability(currentSession);
			variability.getPresencecondition().add(presenceCondition);			
	}

	private Variability getCreateVariability(Session currentSession) {
		for (Resource currentResource : currentSession.getSemanticResources()) {
			if (currentResource.getURI()
				.fileExtension().equals(PresenceConditionCreation.variability_EXTENSION)) {
				EObject rootEObject = currentResource.getContents().get(0);
				if (rootEObject instanceof Variability)
					return (Variability) rootEObject;
			}				
		}
		return null;
	}

	public void addElement(Object containerViewObject, Object copiedViewObject) {
		if (containerViewObject instanceof DSemanticDiagram && copiedViewObject instanceof DDiagramElement) {
			DSemanticDiagram containerSemDiag = (DSemanticDiagram) containerViewObject;
			containerSemDiag.getOwnedDiagramElements().add((DDiagramElement) copiedViewObject);			
		}		
	}
	
	public void addArc(EObject eObjectContainer, Arc arc) {
		arc.setName(arc.getName() + add2Name);
		copyElement(eObjectContainer, arc, "arcs");
	}
	
	public void addPlace(EObject eObjectContainer, Place place) {
		place.setName(place.getName() + add2Name);
		copyElement(eObjectContainer, place, "places");
	}
	
	public void addTransition(EObject eObjectContainer, Transition trans) {
		trans.setName(trans.getName() + add2Name);
		copyElement(eObjectContainer, trans, "trans");
	}

	@SuppressWarnings("unchecked")
	private void copyElement(EObject eObjectContainer, EObject eObjectElement, String featureName) {
		EStructuralFeature feature = eObjectContainer.eClass().getEStructuralFeature(featureName);
		((EList<EObject>) eObjectContainer.eGet(feature,true)).add(eObjectElement);
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}
}
