package solver.formulas;

import solver.features.IFeatureProvider;
import solver.formulas.cnf.CNFFormula;
import solver.presenceConditions.Operator;

public class ImpliesFormula extends BinaryFormula {
	public ImpliesFormula(BoolFormula l, BoolFormula r) {
		this.left = l;
		this.right = r;
	}

	@Override
	public CNFFormula toCNF() {
		BoolFormula equiv = new OrFormula(new NotFormula(left), right);
		return equiv.toCNF();
	}
	
	@Override
	public boolean eval(IFeatureProvider fp) {
		return !this.left.eval(fp) || this.right.eval(fp);
	}
	
	public Operator toOperator() {
		return Operator.IMPLIES;
	}
}
