package org.variability.definition.diagram.design.view;

import org.eclipse.jface.viewers.ITreeContentProvider;

import de.ovgu.featureide.fm.core.base.IFeatureModelStructure;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;

public class FeatureContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof IFeatureModelStructure) {
			IFeatureModelStructure structure = (IFeatureModelStructure) inputElement;
			return new Object[] {structure.getRoot()};
		}		
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IFeatureStructure) {
			IFeatureStructure featureStructure = (IFeatureStructure) parentElement; 
			return featureStructure.getChildren().toArray();
		}		
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IFeatureStructure) {
			IFeatureStructure featureStructure = (IFeatureStructure) element;
			if (featureStructure.getChildrenCount() > 0)
				return true;
		}		
		return false;
	}
	
}
