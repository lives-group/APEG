package apeg.parse.ast;

public interface ConstraintPegNode extends PegNode {
	/**
	 * @return expression which must evaluate to a boolean value
	 */
	public ExprNode getExpr();
}
