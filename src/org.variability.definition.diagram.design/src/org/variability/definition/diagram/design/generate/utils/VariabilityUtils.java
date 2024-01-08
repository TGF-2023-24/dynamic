package org.variability.definition.diagram.design.generate.utils;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.sirius.business.api.dialect.command.RefreshRepresentationsCommand;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.diagram.description.filter.FilterDescription;
import org.eclipse.sirius.diagram.tools.api.command.ChangeLayerActivationCommand;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.variability.definition.diagram.design.Activator;

public final class VariabilityUtils {
	
	// Variability Suffix
	public static final String VARIABILITY_SUFFIX = "variability";
	// Base Variability 
	public static final String BASE_VARIABILITY = "/base-varibility-description/variability.odesign";
	// Id of the Filter to Hide Elements taking into account the feature configuration
	public static final String FILTER_VARIABILITY_NAME = "HidePNElements";
	public static final String FILTER_HIDE_ATTACHED_PC ="HidePNElementsWOPC";	
	
	public static final Resource variabilityResource(ResourceSet reset) {
		return reset.getResource(URI.createPlatformPluginURI(Activator.PLUGIN_ID + BASE_VARIABILITY, false), true);
	}
	
	public static final Group getVariabilityDescriptionGroup(ResourceSet reset) {
		Resource variabilityResource = variabilityResource(reset);
		EObject groupEObject = variabilityResource.getContents().get(0);
		if (groupEObject instanceof Group) {
			return (Group) groupEObject;			
		}		
		return null;
	}
	
	public static final Group copyOfBaseVariabilityGroup(ResourceSet reset) {
		Group  group = getVariabilityDescriptionGroup(reset);
		Copier copier = new Copier();
		Group resultGroup = (Group) copier.copy(group);
		copier.copyReferences();		
		return resultGroup;
	}
	
	public static final DDiagramEditor getActiveSiriusDiagram() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IEditorPart editorPart = wb.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof DDiagramEditor)
			return (DDiagramEditor) editorPart;
		return null;
	}
	
	public static final Resource getPetrinetResource(Collection<Resource> resources) {
		Iterator<Resource> itResources = resources.iterator();
		while (itResources.hasNext()) {
			Resource resource = (Resource) itResources.next();
			if (resource.getURI().fileExtension().equals("petrinets")) {
				return resource;
			}
		}		
		return null;
	}	
	
	public static final FilterDescription findFilterDescription(DDiagram siriusDiagram, String filterId) {
		return existHidePNFilterDescription(siriusDiagram.getDescription().getFilters(), filterId);		
	}
	
	public static final FilterDescription findifActiveFilterDescription(DDiagram siriusDiagram, String filterId) {
		return existHidePNFilterDescription(siriusDiagram.getActivatedFilters(), filterId);		
	}
	
	private static final FilterDescription existHidePNFilterDescription(EList<FilterDescription> listOfFilterDescriptions, 
			String filterId) {
		Iterator<FilterDescription> itFilterDescriptions = listOfFilterDescriptions.iterator();
		while (itFilterDescriptions .hasNext()) {
			FilterDescription filterDescription = (FilterDescription) itFilterDescriptions.next();
			if (filterDescription.getName().equals(filterId))
				return filterDescription;
		}
		return null;
	}	
	
	public static void activateFilterPNHideElements(DDiagram siriusDiagram, String filterId) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(siriusDiagram);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				siriusDiagram.getActivatedFilters().add(VariabilityUtils.findFilterDescription(siriusDiagram,filterId));				
			}			
		});
	}
	
	public static void deActivateFilterPNHideElements(DDiagram siriusDiagram, String filterId) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(siriusDiagram);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				siriusDiagram.getActivatedFilters().remove(VariabilityUtils.findFilterDescription(siriusDiagram, filterId));				
			}			
		});
	}	
	
	public static void layerActivationCommand(Layer petrinetInvlayer, DDiagramEditor siriusEditor) {
		ChangeLayerActivationCommand layerCommand = new ChangeLayerActivationCommand
				(siriusEditor.getSession().getTransactionalEditingDomain(), 
						(DDiagram) siriusEditor.getRepresentation(), petrinetInvlayer, new NullProgressMonitor());		
		siriusEditor.getSession().getTransactionalEditingDomain().getCommandStack().execute(layerCommand);	
	}
	
	public static void refreshDiagram(DDiagramEditor siriusEditor) {
		siriusEditor.getSession().getTransactionalEditingDomain()
			.getCommandStack().execute(new RefreshRepresentationsCommand(
					siriusEditor.getSession().getTransactionalEditingDomain(), 
					new NullProgressMonitor(), siriusEditor.getRepresentation()));
	}
}
