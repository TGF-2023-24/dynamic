package pns.pnpl.design.action.presence.condition;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.tools.api.command.semantic.AddSemanticResourceCommand;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.w3c.dom.NodeList;

import PetriNets.Arc;
import PetriNets.Place;
import PetriNets.Transition;
import pns.pnpl.design.action.presence.condition.utils.FeatureUtils;
import variability.Feature;
import variability.FileURI;
import variability.PresenceCondition;
import variability.Variability;
import variability.impl.VariabilityFactoryImpl;

public class PresenceConditionCreation implements IExternalJavaAction {
	
	public static final String variability_EXTENSION = "vrb";
			
	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {		
		Object element = parameters.get("element");
		if (element instanceof Place || element instanceof Transition || element instanceof Arc) {
			EObject eObjectElement = (EObject) element;
			Session currentSession = SessionManager.INSTANCE.getSession(eObjectElement);
			Variability variability = getCreateVariabilityResource(currentSession, eObjectElement);
			String feature = getFeature(eObjectElement);
			// Create Presence Condition
			PresenceCondition pc = createPresenceCondition(eObjectElement,feature);
			variability.getPresencecondition().add(pc);
			try {
				pc.eResource().save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}				
		}		
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}
	
	public static Variability getCreateVariabilityResource(Session currentSession, EObject element) {
		URI presenceCondURI = element.eResource().getURI().trimFileExtension().appendFileExtension(variability_EXTENSION);
		boolean pcExistResource = currentSession.getTransactionalEditingDomain().getResourceSet().getURIConverter().exists(presenceCondURI, null);		
		if (pcExistResource == false) {
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource pcResource = resourceSet.createResource(presenceCondURI);
			// create variability
			Variability variability = createVariability(element.eResource().getURI().lastSegment());
			pcResource.getContents().add(variability);	
			try {
				pcResource.save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			AddSemanticResourceCommand addSemanticResourceCmd = new AddSemanticResourceCommand(currentSession, presenceCondURI, new NullProgressMonitor());
			currentSession.getTransactionalEditingDomain().getCommandStack().execute(addSemanticResourceCmd);
			return variability;
		} else {
			EObject rootEObject = currentSession.getTransactionalEditingDomain().getResourceSet().getResource(presenceCondURI, true).getContents().get(0);
			if (rootEObject instanceof Variability)
				return (Variability) rootEObject;
			else 
				return null;
		}					
	}
	
	public static Variability createVariability(String pnImportURI) {
		// create variability
		Variability variability = VariabilityFactoryImpl.eINSTANCE.createVariability();
		// Petrinet FileUri
		FileURI pnFileUri = VariabilityFactoryImpl.eINSTANCE.createFileURI();
		pnFileUri.setImportURI(pnImportURI);
		variability.setPetrinet(pnFileUri);
		// File Uri Feature Model
		FileURI fmFileUri = VariabilityFactoryImpl.eINSTANCE.createFileURI();
		fmFileUri.setImportURI(FeatureUtils.FILE_URI_FEATURE_MODEL);	
		variability.setFeaturemodel(fmFileUri);
		return variability;
	}
	
	private PresenceCondition createPresenceCondition(EObject eObjectElement, String feature) {
		PresenceCondition pc = VariabilityFactoryImpl.eINSTANCE.createPresenceCondition();
		pc.getElements().add(eObjectElement);
		Feature featExpression = VariabilityFactoryImpl.eINSTANCE.createFeature();
		featExpression.setFeature(feature);
		pc.setExpression(featExpression);		
		return pc;
	}
	
	private String getFeature(EObject eObjectElement) {
		NodeList features = FeatureUtils.getFeaturesContainerURI(eObjectElement.eResource().getURI().trimSegments(1));
		if (features != null && features.getLength() > 0) {
		   	return features.item(0).getAttributes().getNamedItem("name").getNodeValue();
		} else
		    return "invalid feature";		
	}
	
}
