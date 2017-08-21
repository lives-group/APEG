package apeg.parse.ast.impl;

import apeg.parse.ast.BinaryExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class BinaryExprNodeImpl implements BinaryExprNode {

	private ExprNode left, right;
	private Operator op;
	
	public BinaryExprNodeImpl(ExprNode left, ExprNode right, Operator op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}

	@Override
	public Operator getOperation() {
		return op;
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
