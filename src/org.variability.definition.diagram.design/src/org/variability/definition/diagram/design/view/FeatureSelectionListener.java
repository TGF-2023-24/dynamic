package org.variability.definition.diagram.design.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import de.ovgu.featureide.fm.core.configuration.Selection;

import org.eclipse.swt.widgets.TreeItem;

public class FeatureSelectionListener implements Listener {

	@Override
	public void handleEvent(Event event) {
		Widget widget = event.item;
		if (widget instanceof TreeItem && event.detail == SWT.CHECK) {
			TreeItem treeItem = (TreeItem) widget;			
			String feature = treeItem.getText();
			if (treeItem.getChecked() == true) {
				checkParent(treeItem.getParentItem());
				FilterPetrinet.getInstance().getConfiguration().setManual(feature, Selection.SELECTED);
			} else {				
				FilterPetrinet.getInstance().getConfiguration().setManual(feature, Selection.UNSELECTED);
			}
		}		
	}	
	
	private void checkParent(TreeItem treeItem) {
		if (treeItem.getChecked() == false) {
			treeItem.setChecked(true);
			checkParent(treeItem.getParentItem());
		}		
	}

}
