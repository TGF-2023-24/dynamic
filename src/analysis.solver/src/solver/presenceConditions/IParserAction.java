package solver.presenceConditions;

public interface IParserAction {
	default void exec (FormulaFeature f1, FormulaFeature f2, Operator op) {}
}
