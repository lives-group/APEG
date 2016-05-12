package apeg.parse.ast.impl;

import apeg.parse.ast.ExprNode;
import apeg.parse.ast.MetaPegExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class MetaPegExprNodeImpl implements MetaPegExprNode {

	private ExprNode expr;
	
	public MetaPegExprNodeImpl(ExprNode expr) {
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
