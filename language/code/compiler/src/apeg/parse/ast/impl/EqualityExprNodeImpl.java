package apeg.parse.ast.impl;

import apeg.parse.ast.EqualityExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class EqualityExprNodeImpl implements EqualityExprNode {

	private ExprNode left, right;
	private EqualityOperator op;
	
	public EqualityExprNodeImpl(ExprNode left, ExprNode right, EqualityOperator op) {
		this.left = left;
		this.right = right;
		this.op = op;
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
	public EqualityOperator getEqualityType() {
		return op;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
