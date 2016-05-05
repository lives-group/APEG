package apeg.parse.ast.impl;

import apeg.parse.ast.ConstraintPegNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class ConstraintPegNodeImpl implements ConstraintPegNode {

	private ExprNode expr;
	
	public ConstraintPegNodeImpl(ExprNode expr) {
		this.expr = expr;
	}
	
	@Override
	public ExprNode getExpr() {
		return expr;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
