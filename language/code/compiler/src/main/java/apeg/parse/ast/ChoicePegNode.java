package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class ChoicePegNode extends PegNode {

	private PegNode left, right;
	
	public ChoicePegNode(PegNode left, PegNode right) {
		this.left = left;
		this.right = right;
	}
	
	public PegNode getLeftPeg() {
		return left;
	}

	public PegNode getRightPeg() {
		return right;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
