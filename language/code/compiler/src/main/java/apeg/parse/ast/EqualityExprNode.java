package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class EqualityExprNode extends ExprNode {

	private ExprNode left, right;
	private EqualityOperator op;
	
	public EqualityExprNode(ExprNode left, ExprNode right, EqualityOperator op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}
	
	public ExprNode getLeftExpr() {
		return left;
	}

	public ExprNode getRightExpr() {
		return right;
	}

	public EqualityOperator getEqualityType() {
		return op;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

	public enum EqualityOperator {
		NE, EQ;
	}
	
}
