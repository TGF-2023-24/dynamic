package pns.pnpl.design.action.presence.condition.service;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.variability.definition.diagram.design.condition.PresenceConditionCreation;

import pnpl.featureide.helper.EMFHandler;
import variability.Variability;

public class FilterResourceProvider {
	
	private EMFHandler emfHandler;
	private Variability vRoot;
	    
    //Initialization of the View
  	public FilterResourceProvider(Session session) {
  		this.emfHandler = new EMFHandler();  		
  		this.vRoot = getVariability(session);
  	} 
  	
  	private Variability getVariability(Session session) {
		Iterator<Resource> itResources = session.getSemanticResources().iterator();
		while (itResources.hasNext()) {
			Resource resource = (Resource) itResources.next();
			if (resource.getURI().fileExtension().equals(PresenceConditionCreation.variability_EXTENSION)) {
				EObject rootEObject = resource.getContents().get(0);
				if (rootEObject instanceof Variability)
					return (Variability) rootEObject;				
			}
		}
		return null;
	}
  	
  	public Variability getvRoot() {
		return vRoot;
	}  
    
    public EMFHandler getEmfHandler() {
		return emfHandler;
	}
}
