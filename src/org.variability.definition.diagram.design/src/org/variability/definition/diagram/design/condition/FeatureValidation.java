package org.variability.definition.diagram.design.condition;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.ui.util.WorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.sirius.business.api.query.DRepresentationQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DNodeList;
import org.eclipse.sirius.diagram.business.api.query.EObjectQuery;
import org.eclipse.sirius.diagram.ui.business.api.view.SiriusGMFHelper;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;
import org.eclipse.sirius.diagram.ui.tools.internal.marker.SiriusMarkerNavigationProviderSpec;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.variability.definition.diagram.design.generate.utils.FeatureUtils;
import org.w3c.dom.NodeList;

import variability.BinaryExpression;
import variability.Expression;
import variability.Feature;
import variability.PresenceCondition;
import variability.UnaryExpression;
import variability.Variability;

@SuppressWarnings("restriction")
public class FeatureValidation implements IExternalJavaAction{
	
	/*
	 * class SiriusMarkerNavigationProvider.MARKER_TYPE
	 * 
	 * */	
	
	public static final String MARKER_TYPE = DiagramUIPlugin.ID + ".diagnostic";
	
	/*
	 * class NavigationMarkerConstants
	 * 
	 * The name of marker's attributes
	 * */	
	public static final String DIAGRAM_DESCRIPTOR_URI = "DIAGRAM_DESCRIPTOR_URI";
	
	public static final String SEMANTIC_URI = "SEMANTIC_URI"; 
	
	/**
	 * END
	 * */
	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		Object objectPC = parameters.get("presenceCondition");
		Object containerViewPC = parameters.get("containerView");
		Object removeMarkers = parameters.get("removeMarkers");
		DNodeList pcNodeList = null;
		if (containerViewPC instanceof DNodeList) {
			pcNodeList = (DNodeList) containerViewPC;			
		} else
			return;	
		Session currentSession = SessionManager.INSTANCE.getSession(pcNodeList);
		Resource sessionResource = currentSession.getSessionResource();
		IFile target = sessionResource != null ? WorkspaceSynchronizer.getFile(sessionResource) : null;
		IEditorPart activeEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		WorkbenchPartDescriptor workbenchPartDescriptor = new WorkbenchPartDescriptor(activeEditorPart.getSite().getId(), 
										activeEditorPart.getClass(), activeEditorPart.getSite().getPage());
		IWorkbenchPart workbenchPart = workbenchPartDescriptor.getPartPage().getActivePart();	
		IDiagramWorkbenchPart part = null;
		DiagramEditPart diagEditPart = null;
		if (workbenchPart instanceof IDiagramWorkbenchPart) {
			part = (IDiagramWorkbenchPart) workbenchPart;
			diagEditPart = part.getDiagramEditPart();				
		} else
			return;		
		if (removeMarkers.equals("true"))
			deleteMarkersResource(target, pcNodeList, diagEditPart);
		if (objectPC instanceof PresenceCondition) {
			Expression parentExpression = ((PresenceCondition) objectPC).getExpression();
			validateAllFeaturesInPC(parentExpression, target, pcNodeList, diagEditPart);			
		}		
	}	
	
	public static void deleteMarkersResource(IFile target, DNodeList pcNodeList, DiagramEditPart diagEditPart) {
		View view = getCorrespondingView(pcNodeList, diagEditPart);
		String elementId = view.eResource().getURIFragment(view);
		try {
			IMarker[] markers = target.findMarkers(MARKER_TYPE, true, IResource.DEPTH_ZERO);
			for (int i = 0; i < markers.length; i++) {
				Object objectElementId = markers[i].getAttribute(org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID);
				if (objectElementId.equals(elementId))
					markers[i].delete();
			}			
		} catch (CoreException e) {
			e.printStackTrace();
		}		
	}
	
	public static void validateAllFeaturesInPC(Expression parentExpression, IFile target, EObject pcNodeList, DiagramEditPart diagEditPart) {
		EObject pcEObject = parentExpression.eContainer();
		EObject containerPC = pcEObject.eContainer();			
		if (containerPC instanceof Variability) {
			Variability variability = (Variability) containerPC;
			URI featureModelUri = variability.eResource().getURI().trimSegments(1).appendSegment(variability.getFeaturemodel().getImportURI());
			NodeList features = FeatureUtils.getFeatures(featureModelUri);
			if (features.getLength() > 0) {
				Stack<Expression> expressions = new Stack<Expression>();
				expressions.push(parentExpression);															
				while (!expressions.isEmpty()) {
					Expression currentExpression = expressions.pop();
					if (currentExpression instanceof UnaryExpression) {
						expressions.push(((UnaryExpression) currentExpression).getRight());
					} else if (currentExpression instanceof BinaryExpression) {
						expressions.push(((BinaryExpression) currentExpression).getLeft());
						expressions.push(((BinaryExpression) currentExpression).getRight());
					} else if (currentExpression instanceof Feature) {
						String feature =  ((Feature) currentExpression).getFeature();		
						boolean existFeature = FeatureUtils.existFeature(feature, features);
						if (existFeature == false) {
							//insert marker
							String message = "Feature " + feature + " does not exist";
							createmarkersValidateAction(target, pcNodeList, diagEditPart, message, IStatus.ERROR);
						}
					}
				}					
			}
		}	
	}

	/*
	 * 
	 * Create marker associated to EObject  
	 * */	
	public static void createmarkersValidateAction(IFile target, EObject eObject, DiagramEditPart diagEditPart, String message, int statusSeverity) {
		View view = getCorrespondingView(eObject, diagEditPart);
		addMarkerValidateAction(diagEditPart.getViewer(), target, view, EMFCoreUtil.getQualifiedName(eObject, true), message,
				statusSeverity);
	}
	
	private static void addMarkerValidateAction(EditPartViewer viewer, IFile target, View view, String location, String message,
			int diagnosticToStatusSeverity) {
		if (target == null) {
			return;
	    }
	    String elementId = view.eResource().getURIFragment(view);
	    // Search semantic URI
	    String semanticURI = null;
	    EObject ddiagramElement = view.getElement();
	    if (ddiagramElement instanceof DSemanticDecorator) {
	    	EObject semanticElement = ((DSemanticDecorator) ddiagramElement).getTarget();
	        semanticURI = EcoreUtil.getURI(semanticElement).toString();
	    }
	    // Search diagram URI
	    Object object = viewer.getFocusEditPart().getModel();
	    String diagramDescriptorUri = getDRepresentationDescriptorURIFromDiagram(object);
	    SiriusMarkerNavigationProviderSpec.addMarker(target, elementId, diagramDescriptorUri, semanticURI, location, message, diagnosticToStatusSeverity);	   	
	}	 
	 /*
	  * Sirius
	  * class ValidateAction.getDRepresentationDescriptorURIFromDiagram
	  * */
	 private static String getDRepresentationDescriptorURIFromDiagram(Object object) {
		 String diagramDescriptorUri = null;
	     if (object instanceof Diagram) {
	    	 Optional<DRepresentationDescriptor> optional = Optional.of((Diagram) object).map(View::getElement).filter(DDiagram.class::isInstance).map(d -> {
	         DRepresentationQuery query = new DRepresentationQuery((DDiagram) d);
	         return query.getRepresentationDescriptor();
	         });
	    	 if (optional.isPresent()) {
	    		 final URI uri = EcoreUtil.getURI(optional.get());
	             diagramDescriptorUri = uri.toString();
	             }
	    	 }
	     return diagramDescriptorUri;
	 }
	 
	 /*
	  * Sirius 
	  * class ValidateAction.getCorrespondingView
	  * */
	 private static View getCorrespondingView(EObject element, DiagramEditPart diagramEditPart) {
		 DSemanticDecorator dSemanticDecorator = getDSemanticDecorator(element, diagramEditPart);
	     View view = null;
	     if (dSemanticDecorator != null) {
	    	 view = SiriusGMFHelper.getGmfView(dSemanticDecorator);
	     }
	     if (view == null) {
	    	 view = diagramEditPart.getDiagramView();
	     }
	     return view;
	 }
	 
	 /**
	  * Sirius 
	  * class ValidateAction
	  **/	 
	 private static DSemanticDecorator getDSemanticDecorator(EObject element, DiagramEditPart diagramEditPart) {
		 DSemanticDecorator dSemanticDecorator = null;
	     if (!(element instanceof DSemanticDecorator)) {
	    	 Collection<EObject> xrefs = new EObjectQuery(element).getInverseReferences(ViewpointPackage.Literals.DSEMANTIC_DECORATOR__TARGET);
	         DDiagram dDiagram = (DDiagram) diagramEditPart.getDiagramView().getElement();
	         for (EObject eObject : xrefs) {
	        	 if (eObject == dDiagram || eObject instanceof DSemanticDecorator && EcoreUtil.isAncestor(dDiagram, eObject)) {
	        		 dSemanticDecorator = (DSemanticDecorator) eObject;
	        		 break;
	        	 }
	        }
	     } else {
	    	 dSemanticDecorator = (DSemanticDecorator) element;
	     }
	     return dSemanticDecorator;
	 }
	
	 //End methods to create markers in a Sirius Diagram	 
	
	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
