package apeg.parse.ast.impl;

import apeg.parse.ast.ChoicePegNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class ChoicePegNodeImpl implements ChoicePegNode {

	private PegNode left, right;
	
	public ChoicePegNodeImpl(PegNode left, PegNode right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public PegNode getLeftPeg() {
		return left;
	}

	@Override
	public PegNode getRightPeg() {
		return right;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
