package org.variability.definition.diagram.design.generate.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import pcdef.PCDiagramDefinition;

public class PCDiagramDefinitionUtils {
	
	public static final Resource loadPCDiagramResource(IFile file) {
		ResourceSet reset = new ResourceSetImpl();			
		return reset.getResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true), true);
	}	
	
	public static final PCDiagramDefinition getDiagramDefinitionRoot(Resource resource) {
		return (PCDiagramDefinition) resource.getContents().get(0);
	}	
}
