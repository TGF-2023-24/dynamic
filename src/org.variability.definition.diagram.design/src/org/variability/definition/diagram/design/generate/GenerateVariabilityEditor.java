package org.variability.definition.diagram.design.generate;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.variability.definition.diagram.design.generate.utils.PCDiagramDefinitionUtils;
import org.variability.definition.diagram.design.generate.utils.VariabilityUtils;
import org.variability.definition.diagram.design.vp.ViewpointSpecificationProjectExtended;

public class GenerateVariabilityEditor implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// Do nothing
	}

	@Override
	public void dispose() {
		// Do nothing
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
		if (structured.getFirstElement() instanceof IFile) {
			// get the selected file
			IFile pcDefFile = (IFile) structured.getFirstElement();
			//Load Resource
			Resource pcDefResource = PCDiagramDefinitionUtils.loadPCDiagramResource(pcDefFile);
			IProject project = pcDefFile.getProject();
			new CreateVariabilityArtifacts(pcDefResource, project.getName() + "." + VariabilityUtils.VARIABILITY_SUFFIX,
					pcDefFile.getName() + "-" + VariabilityUtils.VARIABILITY_SUFFIX + "." + ViewpointSpecificationProjectExtended.VIEWPOINT_MODEL_EXTENSION)
			.doGenerate();			
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
