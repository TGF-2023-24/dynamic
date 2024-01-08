package org.variability.definition.diagram.design.view;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.ovgu.featureide.fm.core.base.impl.FeatureStructure;

public class CFeatureColumn extends ColumnLabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof FeatureStructure) {
			FeatureStructure featStructure = (FeatureStructure) element; 
			return featStructure.getFeature().getName();
		}
		return super.getText(element);
	}
}
