package org.variability.definition.diagram.design.view;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;

public class ESFeatureColumn extends EditingSupport {

	public ESFeatureColumn(ColumnViewer viewer) {
		super(viewer);		
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return null;
	}

	@Override
	protected boolean canEdit(Object element) {
		return false;
	}

	@Override
	protected Object getValue(Object element) {
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		
	}
}
