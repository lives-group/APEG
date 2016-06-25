package apeg.parse.ast.impl;

import apeg.parse.ast.ExprNode;
import apeg.parse.ast.NotExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class NotExprNodeImpl implements NotExprNode {
	
	private ExprNode expr;
	
	public NotExprNodeImpl(ExprNode expr) {
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
