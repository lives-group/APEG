package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class OrExprNode extends ExprNode {
	
	private ExprNode left, right;
	
	public OrExprNode(ExprNode left, ExprNode right) {
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
