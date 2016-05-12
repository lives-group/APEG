package apeg.parse.ast.impl;

import apeg.parse.ast.ExprNode;
import apeg.parse.ast.MinusExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class MinusExprNodeImpl implements MinusExprNode {
	
	private ExprNode expr;
	
	public MinusExprNodeImpl(ExprNode expr) {
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
