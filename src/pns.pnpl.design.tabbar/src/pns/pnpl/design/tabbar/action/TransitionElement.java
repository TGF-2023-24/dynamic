package pns.pnpl.design.tabbar.action;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.sirius.diagram.DNode;

import PetriNets.Transition;

public class TransitionElement extends PropertyTester {

	public TransitionElement() {
		// Do Nothing
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof GraphicalEditPart) {
			GraphicalEditPart graphicalEditPart = (GraphicalEditPart) receiver;
			Object modelObject = graphicalEditPart.getModel();
			if (modelObject instanceof NodeImpl) {
				NodeImpl node = (NodeImpl) modelObject;
				EObject nodeElement = node.getElement();
				if (nodeElement instanceof DNode) {
					DNode dNode = (DNode) nodeElement;
					EObject target = dNode.getTarget();
					if (target instanceof Transition) {
						return true;						
					}
				}
			}				
		}			
		return false;
	}

}
