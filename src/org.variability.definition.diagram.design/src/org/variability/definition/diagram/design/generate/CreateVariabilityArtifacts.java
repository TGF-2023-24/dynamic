package org.variability.definition.diagram.design.generate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.variability.definition.diagram.design.generate.template.WorkflowVariabilityEditor;
import org.variability.definition.diagram.design.generate.utils.PCDiagramDefinitionUtils;
import org.variability.definition.diagram.design.generate.utils.VariabilityUtils;
import org.variability.definition.diagram.design.vp.ViewpointSpecificationProjectExtended;

import pcdef.PCDiagramDefinition;


public class CreateVariabilityArtifacts {
	
	private Resource pcDefResource;
	private String projectName;
	private String odesignName;
		
	public CreateVariabilityArtifacts(Resource pcDefResource, String projectName, String odesignName) {
		this.pcDefResource = pcDefResource;
		this.projectName = projectName;	
		this.odesignName = odesignName;
	}
	
	public void doGenerate() {
		//ResourceSet
		ResourceSet reset = this.pcDefResource.getResourceSet();
		//load resources
		Group groupBaseVariability = VariabilityUtils.copyOfBaseVariabilityGroup(reset);
		PCDiagramDefinition diagramDefinition = PCDiagramDefinitionUtils.getDiagramDefinitionRoot(this.pcDefResource);
		Viewpoint toExtendVP = ViewpointRegistry.getInstance().getViewpoint(URI.createURI(diagramDefinition.getViewpointUri()));
		// 1. Create Viewpoint Specification Project
		IProject viewpointProject = createViepointProject(this.odesignName, groupBaseVariability);
		groupBaseVariability.setName("Variability-" + ((Group) toExtendVP.eContainer()).getName());
		//2. Refine initDescriptionResource
		new RefineInitVaribilityDescription(this.projectName,diagramDefinition, groupBaseVariability.getOwnedViewpoints().get(0), toExtendVP).
					doExtendInitDescription();
		try {
			//3. Generate files (e.g. PresenceConditionServices)
	        generateFiles(groupBaseVariability, viewpointProject);
	        //4. Refresh project
	        viewpointProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());			
		} catch (CoreException e) {			
			e.printStackTrace();
		}       
	}	
	
	private void generateFiles(EObject vpBaseVariability, IResource viewpointProject) {		
        final List<String> generatorargs = new ArrayList<String>();
        generatorargs.add(this.projectName);
        generatorargs.add(this.odesignName.substring(0, this.odesignName.lastIndexOf('.')));
        try {
        	 WorkflowVariabilityEditor generateFiles = new WorkflowVariabilityEditor(vpBaseVariability, new File(viewpointProject.getLocation().toFile().toString()),
             		generatorargs);
        	boolean before = switchSuccessNotification(false);
			generateFiles.doGenerate(BasicMonitor.toMonitor(new NullProgressMonitor()));
			switchSuccessNotification(before);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}	

	private IProject createViepointProject(String extendedModelName, Group groupBaseVariability) {
		try {
			return ViewpointSpecificationProjectExtended.
					createNewViewpointSpecificationProject(this.projectName, this.odesignName, groupBaseVariability);
		} catch (CoreException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean switchSuccessNotification(boolean notify) {
		boolean successNotifications = AcceleoPreferences.areSuccessNotificationsEnabled();
		if(successNotifications != notify)
		{
			AcceleoPreferences.switchSuccessNotifications(notify);
			return successNotifications;
		}
		return successNotifications;
	}	
}
