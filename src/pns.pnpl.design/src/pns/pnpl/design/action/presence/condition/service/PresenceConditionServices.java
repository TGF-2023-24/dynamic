package pns.pnpl.design.action.presence.condition.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DNodeList;
import org.eclipse.sirius.diagram.business.api.query.EObjectQuery;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.variability.definition.diagram.design.view.FilterPetrinet;
import org.variability.definition.diagram.design.view.InvariantPetriNetView;

import PetriNets.Arc;
import PetriNets.PetriNet;
import PetriNets.Place;
import PetriNets.Transition;
import pnpl.featureide.composer.FeatureIDEProvider;
import pnpl.featureide.composer.PetriNetsComposer;
import pns.pnpl.design.action.presence.condition.FeatureValidation;
import solver.presenceConditions.ConditionParser;
import variability.BinaryExpression;
import variability.Expression;
import variability.Feature;
import variability.PresenceCondition;
import variability.UnaryExpression;

public class PresenceConditionServices {
	
	private static FilterResourceProvider filterResourceProvider = null;
	
	// Hide the elements that belongs to the model, because the PC returns true
	public boolean getHidePNElement(EObject element) {		
		if (filterResourceProvider == null)
			filterResourceProvider = new FilterResourceProvider(SessionManager.INSTANCE.getSession(element));
		if (FilterPetrinet.getInstance() != null) {
			if (element instanceof PresenceCondition) {
				PresenceCondition presCond = (PresenceCondition) element;
				Iterator<EObject> itElements = presCond.getElements().iterator();
				int count = 0;
				while (itElements.hasNext()) {
					EObject eObject = (EObject) itElements.next();
					if (isElementPresented(eObject) == false)
						count++;
				}
				if (count == presCond.getElements().size())
					return false;
			} else {
				return isElementPresented(element);				
			}
		}
		return true;
	}
	
	// Hide items that do not have a PC attached
	public boolean getHidePNElementWoPC(EObject element) {
		if (filterResourceProvider == null)
			filterResourceProvider = new FilterResourceProvider(SessionManager.INSTANCE.getSession(element));
		if (!(element instanceof PresenceCondition) && !(element instanceof Expression)) {
			String qualifiedName = PetriNetsComposer.getNameOf(element);
			if (qualifiedName != null) {
				PresenceCondition pc = filterResourceProvider.getvRoot()
						.getPresencecondition(qualifiedName);
				if (pc == null)
					return true;
			}		
		} 
		return false;
	}
	
	public static boolean isElementPresented(EObject element) {
		String qualifiedName = PetriNetsComposer.getNameOf(element);
		FeatureIDEProvider fip = new FeatureIDEProvider(FilterPetrinet.getInstance().getConfiguration());
		if (qualifiedName != null) {
			PresenceCondition pc = filterResourceProvider.getvRoot()
					.getPresencecondition(qualifiedName);
			ConditionParser cp = new ConditionParser(filterResourceProvider.
					getEmfHandler().object2xtext(pc.getExpression()), 
					fip);		
			return cp.eval();	
		}
		return true;
	}

	public String getLabelParentExpression(Expression expression) {
		return getLabelExpression(expression) + ";";
	}
	
	public boolean validateExpression(PresenceCondition presenceCondition) {
		Collection<EObject> presenceCondInverse = new EObjectQuery(presenceCondition).
				getInverseReferences(ViewpointPackage.Literals.DSEMANTIC_DECORATOR__TARGET);
		DNodeList dNodeList = null;
		Iterator<EObject> itInverseObject = presenceCondInverse.iterator();
		while (itInverseObject.hasNext()) {
			EObject eObject = (EObject) itInverseObject.next();
			if (eObject instanceof DNodeList) {
				dNodeList = (DNodeList) eObject;
				break;
			}			
			if (itInverseObject.hasNext() == false)
				return true;
		}
				
		HashMap<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("presenceCondition", presenceCondition);
		parameters.put("containerView", dNodeList);
		parameters.put("removeMarkers", "false");
		FeatureValidation featureValidation = new FeatureValidation();
		featureValidation.execute(null, parameters);		
		return true;	
	}	
	
	private String getLabelExpression(Expression expression) {		
		if (expression instanceof Feature)
			return ((Feature) expression).getFeature();
		else if (expression instanceof UnaryExpression) {
			UnaryExpression unaryExpression = (UnaryExpression) expression;
			return unaryExpression.getOperator().getLiteral().toLowerCase() + " " + getLabelExpression(unaryExpression.getRight());
		}
		else if (expression instanceof BinaryExpression) {
			BinaryExpression binaryExpression = (BinaryExpression) expression;
			return "(" + getLabelExpression(binaryExpression.getLeft()) + " " + binaryExpression.getOperator().getLiteral().toLowerCase() + " "
					+ getLabelExpression(binaryExpression.getRight()) + ")";
		}
		return "Undefined";
	}	
	
	//Methods to highlight the elements that belongs to the Invariant Layer
	//Highlight the corresponding Places
	public EList<EObject> highlightPlaces(PetriNet pn) {
		EList<EObject> highlighPlaces = new BasicEList<EObject>();
		List<String> inv = InvariantPetriNetView.getInstance().getCurrentInvariant();
		for (Place place : pn.getPlaces()) {
			if(inv.indexOf(place.getName()) != -1) {
				highlighPlaces.add(place);
			}
		}		
		return highlighPlaces;
	}
	
	//Highlight the corresponding Transition
	public EList<EObject> highlightTransition(PetriNet pn) {
		EList<EObject> highlighTransition = new BasicEList<EObject>();
		List<String> inv = InvariantPetriNetView.getInstance().getCurrentInvariant();
		for (Transition transition : pn.getTrans()) {
			if(inv.indexOf(transition.getName()) != -1) {
				highlighTransition.add(transition);
			}
		}		
		return highlighTransition;
	}
	
	//Highlight the corresponding arcs
	public boolean highlightArc(Arc arc) {
		EList<EObject> highlighArcs = new BasicEList<EObject>();
		List<String> inv = InvariantPetriNetView.getInstance().getCurrentInvariant();
		if(inv.indexOf(arc.getName()) != -1) {
			return true;			
		}		
		return false;
	}
	
}
