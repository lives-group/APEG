package apeg.parse.ast;

import apeg.parse.ast.visitor.ElementVisitor;

public interface AssignmentNode extends ElementVisitor {
	/**
	 * @return left-hand side variable name
	 */
	public String getVariable();
	/**
	 * @return right-hand side expression
	 */
	public ExprNode getExpr();
}
