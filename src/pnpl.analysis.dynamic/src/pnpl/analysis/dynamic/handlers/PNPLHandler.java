package pnpl.analysis.dynamic.handlers;

import pnpl.analysis.handlers.AbstractAnalysisHandler;

public class PNPLHandler extends AbstractAnalysisHandler {

	@Override
	protected String getExtensionPointIdentifier() {
		return "pnpl.analysis.dynamic.analysis.lines";
	}

}
