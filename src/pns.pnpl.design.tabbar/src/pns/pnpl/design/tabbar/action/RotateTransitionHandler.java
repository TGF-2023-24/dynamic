package pns.pnpl.design.tabbar.action;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class RotateTransitionHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// Do nothing
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get workbench window
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		// set selection service
		ISelectionService service = window.getSelectionService();
		// set structured selection
		IStructuredSelection structured = (IStructuredSelection) service.getSelection();
		//check if it is an IFile
		if (structured.getFirstElement() instanceof GraphicalEditPart) {
			GraphicalEditPart graphicalEditPart = (GraphicalEditPart) structured.getFirstElement();
			Object modelObject = graphicalEditPart.getModel();
			if (modelObject instanceof NodeImpl) {
				NodeImpl node = (NodeImpl) modelObject;
				EObject nodeElement = node.getElement();
				if (nodeElement instanceof DNode) {
					DDiagram diagram = ((DNode) nodeElement).getParentDiagram();
					LayoutConstraint nodeLayoutConstraint = node.getLayoutConstraint();
					if (nodeLayoutConstraint instanceof Bounds) {
						Bounds bounds = (Bounds) nodeLayoutConstraint;
						Session currentSession = SessionManager.INSTANCE.getSession(node);
						TransactionalEditingDomain domain = currentSession.getTransactionalEditingDomain();
						domain.getCommandStack().execute(new RecordingCommand(domain) {			
							@Override
							protected void doExecute() {
								int height = bounds.getHeight();
								bounds.setHeight(bounds.getWidth());
								bounds.setWidth(height);
								// Refresh current diagram				
								DialectManager.INSTANCE.refresh(diagram, new NullProgressMonitor());
							}
						});					
					}
				}				
			}			
		}		
		return null;
	}	
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// Do nothing
	}

}
