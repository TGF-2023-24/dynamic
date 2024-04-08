package pnpl.analysis.dynamic.analysis;

import org.eclipse.core.resources.IFile;

import pnpl.analysis.dynamic.helpers.VariabilityHelper;

public abstract class AbstractAnalysisDynamic {

	protected VariabilityHelper vh = null;
	protected IFile vrbFile = null;
	
	public abstract boolean run();
	
	public void loadVariabilityFile(IFile vrbfile) {
		this.vrbFile = vrbfile; 
		vh = new VariabilityHelper();
		vh.load(vrbfile);	
	}	
}
