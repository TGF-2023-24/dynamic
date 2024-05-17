package pnpl.analysis.dynamic.handlers;

import pnpl.analysis.handlers.AbstractAnalysisHandler;

public class ProductsHandler extends AbstractAnalysisHandler {

	@Override
	protected String getExtensionPointIdentifier() {
		return "pnpl.analysis.dynamic.analysis.products";
	}

}
