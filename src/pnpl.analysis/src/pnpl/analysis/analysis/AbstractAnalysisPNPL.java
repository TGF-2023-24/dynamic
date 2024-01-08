package pnpl.analysis.analysis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import solver.features.impl.FeatureProvider;
import solver.formulas.cnf.CNFClause;
import solver.formulas.cnf.CNFFormula;
import solver.issues.ValidationIssue;
import solver.presenceConditions.ConditionParser;

public abstract class AbstractAnalysisPNPL extends AbstractAnalysis {
	protected Map<String, String> pclist = null;
	
	protected String condition = "";

	protected abstract boolean buildCondition();
	
	public boolean run() {
		if (vh == null) return false;
		System.out.println("[petri nets analysis] Starting analysis of product lines");

		extractPresenceConditions();
		boolean result = buildCondition();
		
		if (!result)
			return false;
		if (condition == null || condition.trim().isEmpty())
			return true;
		
		System.out.println("[petri nets analysis] Condition: "+ condition);
		
		FeatureProvider fp = new FeatureProvider(getFeatures()); 
		ConditionParser parser = new ConditionParser(condition,fp);
		Collection<ValidationIssue> errors = parser.parse();

		CNFFormula formula = parser.getAST().toCNF(); 
		System.out.println("[petri nets analysis] Evaluation: "+ parser.getResult());
		System.out.println("[petri nets analysis] Errors: "+ errors);
		//System.out.println("[petri nets analysis] CNF: " + formula+"\n");
		
		return !doSomeSAT(formula, fp);
	}
	
	private void extractPresenceConditions () {
		pclist = vh.extractPresenceConditions();
	}
	
//	private void extractPresenceConditions () {
//		pclist = Maps.newHashMap();	
//		pcExpression = Maps.newHashMap();	
//		flist = Lists.newArrayList();
//		
//		Variability vrb = vh.getVariability();
//		IFeatureModel fm = vh.getFeatureModel();
//
//		for(PresenceCondition pc : vrb.getPresencecondition()) {
//			for (EObject elem : pc.getElements()) {
//				String key = "";
//				if (elem instanceof Place) {
//					key = ((Place) elem).getName();
//				} else if (elem instanceof Transition) {
//					key = ((Transition) elem).getName();
//				} else if (elem instanceof Arc) {
//					key = ((Arc) elem).getName();				
//				}
//				if (key != "") {
//					pclist.put(key, extractExpression (fm,pc.getExpression()));
//					pcExpression.put(key, pc.getExpression());
//				}
//			}	
//		}
//	}
	
//	private String extractExpression (IFeatureModel fm, EObject expr) {
//		String value = "";
//		if (expr instanceof Feature) { 
//			value = extractParents(fm,((Feature) expr).getFeature());
//		} else if (expr instanceof UnaryExpression){
//			value = "(not " + extractExpression(fm,((UnaryExpression) expr).getRight()) + ")";
//		} else if (expr instanceof BinaryExpression) {
//			BinaryExpression bin = (BinaryExpression) expr;
//			value = "(" + extractExpression(fm,bin.getLeft()) + " " 
//					+ bin.getOperator().getLiteral().toLowerCase() +  " " 
//					+ extractExpression(fm,bin.getRight()) + ")";
//		}
//		return value;
//	}

//	private String extractParents(IFeatureModel fm, String key) {
//		String value = "";
//
//		IFeature f = fm.getFeature(key);
//		if (f != null) {
//			value = key;
//			if(!flist.contains(key))
//				flist.add(key);
//
//			IFeatureStructure parent = f.getStructure().getParent();
//			if (parent != null && parent != fm.getStructure().getRoot()) {
//				String pkey = parent.getFeature().getName();
//				String pp = extractParents(fm,pkey);
//				if (parent.isAnd()) {
//					if(f.getStructure().isMandatory()) 
//						value = "((" + value + " implies " + pp + ") and (" + pp + " implies " + value + "))";
//					else
//						value = "(" + value + " implies " + pp + ")";
//				}
//			}
//		} else
//			System.err.println("[petri nets analysis] Feature " + key + " doesn't exist in the model");
//		return value;
//	}
	
//	private String[] getFeatures() {
//		String[] features = new String[flist.size()];
//		features = flist.toArray(features);
//		return features;
//	}
	
	private String[] getFeatures() {
		List<String> flist = vh.getFeatureModel().getFeatureOrderList();
		String[] features = new String[flist.size()];
		features = flist.toArray(features);
		return features;
	}

	private boolean doSomeSAT(CNFFormula formula, FeatureProvider fp) {
		ISolver solver = SolverFactory.newDefault();
		solver.setTimeout(3600); // 1 hour timeout

		for (CNFClause c : formula.clauses()) {
			try {
				solver.addClause(c.toVecInt(fp.getFeatures()));
			} catch (ContradictionException e) {
				System.err.println("[petri nets analysis] Contradiction");
				//e.printStackTrace();
				return false;
			}
		}

		IProblem problem = solver;
		try {
			if (problem.isSatisfiable()) {
				System.out.println("[petri nets analysis] Problem is SAT");
				System.out.println("[petri nets analysis] Model: ");
				int[] model = problem.findModel();
				for (int i = 0; i < model.length; i++) {
					System.out.println(" "+fp.getFeatures().get(Math.abs(model[i])-1)+" = "+
							((model[i] > 0) ? "true" : "false"));
				}
				return model.length > 0;
			}
		} catch (TimeoutException e) {
			System.err.println("[petri nets analysis] Timeout!");
			e.printStackTrace();
		}
		return false;
	}	
}
