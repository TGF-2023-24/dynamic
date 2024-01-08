package pnpl.analysis.analysis;

import org.eclipse.core.resources.IFile;

import pnpl.analysis.helpers.VariabilityHelper;

public abstract class AbstractAnalysis {

	protected VariabilityHelper vh = null;
	protected IFile vrbFile = null;
	
	public abstract boolean run();
	
	public void loadVariabilityFile(IFile vrbfile) {
		this.vrbFile = vrbfile; 
		vh = new VariabilityHelper();
		vh.load(vrbfile);	
	}	
}
