package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AndExprNode extends ExprNode {

	private ExprNode left, right;
	
	public AndExprNode(ExprNode left, ExprNode right) {
		this.left = left;
		this.right = right;
	}
	
	public ExprNode getLeftExpr() {
		return left;
	}

	public ExprNode getRightExpr() {
		return right;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
