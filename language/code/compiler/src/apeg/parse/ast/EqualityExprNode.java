package apeg.parse.ast;

public interface EqualityExprNode extends ExprNode {
	
	public enum EqualityOperator {
		NE, EQ;
	}
	
	/**
	 * @return left-hand side expression
	 */
	public ExprNode getLeftExpr();
	/**
	 * @return right-hand side expression
	 */
	public ExprNode getRightExpr();
	/**
	 * @return type of the comparator operator: equal or not equal
	 */
	public EqualityOperator getEqualityType();
}
