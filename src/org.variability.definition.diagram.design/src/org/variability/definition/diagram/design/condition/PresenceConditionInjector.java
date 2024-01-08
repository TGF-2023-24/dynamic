package org.variability.definition.diagram.design.condition;

import com.altran.general.integration.xtextsirius.runtime.IXtextLanguageInjector;
import com.google.inject.Injector;

import pnpl_variability.editor.ui.internal.EditorActivator;

public class PresenceConditionInjector implements IXtextLanguageInjector{

	@Override
	public Injector getInjector() {
		return EditorActivator.getInstance().getInjector(EditorActivator.PNPL_VARIABILITY_PNPL_VARIABILITY);
	}

}
