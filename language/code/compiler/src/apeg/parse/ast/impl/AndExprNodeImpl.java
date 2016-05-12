package apeg.parse.ast.impl;

import apeg.parse.ast.AndExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AndExprNodeImpl implements AndExprNode {

	private ExprNode left, right;
	
	public AndExprNodeImpl(ExprNode left, ExprNode right) {
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
