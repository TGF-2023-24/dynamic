package solver.formulas;

import solver.features.IFeatureProvider;
import solver.formulas.cnf.CNFFormula;
import solver.presenceConditions.Operator;


public abstract class BoolFormula {
	public abstract CNFFormula toCNF();
	public abstract boolean eval(IFeatureProvider fp);
	public Operator toOperator() {
		return null;
	}
}
