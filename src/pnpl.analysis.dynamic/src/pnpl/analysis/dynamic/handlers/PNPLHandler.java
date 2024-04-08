package pnpl.analysis.dynamic.handlers;

public class PNPLHandler extends AbstractAnalysisHandler {

	@Override
	protected String getExtensionPointIdentifier() {
		return "pnpl.analysis.dynamic.analysis.lines";
	}

}
