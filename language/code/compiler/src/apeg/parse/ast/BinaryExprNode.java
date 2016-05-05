package apeg.parse.ast;

public interface BinaryExprNode extends ExprNode {
	/**
	 * @return binary operator type
	 */
	public Operator getOperation();	
	/**
	 * @return left-hand side expression
	 */
	public ExprNode getLeftExpr();
	/**
	 * @return right-hand side expression
	 */
	public ExprNode getRightExpr();
	
	public enum Operator {
		GT, GE, LT, LE, // Relational operators
		ADD, SUB, MUL, DIV, MOD; // Arithmetic operators
	}
}
