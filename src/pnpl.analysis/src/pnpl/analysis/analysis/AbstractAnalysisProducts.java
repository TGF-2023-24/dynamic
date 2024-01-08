package pnpl.analysis.analysis;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;

import PetriNets.PetriNet;
import PetriNets.PetriNetsFactory;
import pnpl.analysis.helpers.PetriNetsIterator;
import pnpl.featureide.helper.EMFHandler;

/**
 * Abstract class for the analysis of all derivable products from PNPL
 * @author Elena Gómez-Martínez
 *
 */

public abstract class AbstractAnalysisProducts extends AbstractAnalysis {
	protected PetriNet pn = null;
	private static boolean StrongSatisfaction = true;
	protected IFile currentFile = null; 
	protected Integer productNumber = 0;
	
	protected abstract boolean runProductAnalysis();
	
	public boolean run() {
		if (vh == null) return false;
		if ((vh.getPetriNetFile() == null) || (vh.getFeatureModelFile() == null)) return false;
		
		System.out.println("[petri net products analysis] Starting");

		PetriNetsIterator iterator = new PetriNetsIterator(vh);
		iterator.buildModels();

		EMFHandler emh =  new EMFHandler();
		boolean result = true;
		int count = 0;
		while(iterator.hasNext()) {
			IFile ifile = iterator.next();
			if (ifile != null) {
				System.out.println("[petri net products analysis] Analysing " + ifile.getFullPath());
				
				this.currentFile = ifile;
				this.productNumber = count; 
				
				Resource r = emh.loadModel(ifile.getRawLocation().makeAbsolute().toFile(), PetriNetsFactory.eINSTANCE.getEPackage());
				pn = (PetriNet) r.getContents().get(0);

				count ++;
				if(!runProductAnalysis()) result = false;
				pn = null;
				if (!StrongSatisfaction && !result) break;
			}
		}
		System.out.println("[petri net products analysis] Number of products analyzed: " + count);
		return result;
	}
}
