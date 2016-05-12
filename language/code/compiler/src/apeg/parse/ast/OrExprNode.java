package apeg.parse.ast;

public interface OrExprNode extends ExprNode {
	/**
	 * @return left-hand side expression
	 */
	public ExprNode getLeftExpr();
	/**
	 * @return right-hand side expression
	 */
	public ExprNode getRightExpr();
}
