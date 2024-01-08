package org.variability.definition.diagram.design.generate.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pnpl.analysis.analysis.AbstractAnalysis;

public class InvariantUtils {
	public String P_INVARIANT = "P-Invariant nets";
	public String T_INVARIANT = "T-Invariant nets";

	public void execute (IFile vrb, String type) {
		if (vrb != null) {		
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IConfigurationElement selectedExtension = null;

			// obtain extensions of analysis
			IConfigurationElement[] extensions = registry.getConfigurationElementsFor("pnpl.analysis.analysis.lines");
			for (IConfigurationElement extension : extensions) {
				if (extension.getAttribute("name").equals(type)) {
					selectedExtension = extension;
				}
			}

			if (selectedExtension != null) {
				AbstractAnalysis analysis;
				try {
					analysis = (AbstractAnalysis)selectedExtension.createExecutableExtension("class");
					analysis.loadVariabilityFile(vrb);
					analysis.run();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
