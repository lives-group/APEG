package apeg.parse.ast;

public interface MetaPegExprNode extends ExprNode {
	/**
	 * @return an expression that evaluate to string
	 * future plan: use metaprogramming to express apeg rules
	 */
	public ExprNode getExpr();
}
