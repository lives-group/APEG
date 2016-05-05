package apeg.parse.ast.impl;

import apeg.parse.ast.ExprNode;
import apeg.parse.ast.OrExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class OrExprNodeImpl implements OrExprNode {
	
	private ExprNode left, right;
	
	public OrExprNodeImpl(ExprNode left, ExprNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public ExprNode getLeftExpr() {
		return left;
	}

	@Override
	public ExprNode getRightExpr() {
		return right;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
