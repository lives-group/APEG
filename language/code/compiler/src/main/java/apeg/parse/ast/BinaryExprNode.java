package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class BinaryExprNode extends ExprNode {

	private ExprNode left, right;
	private Operator op;
	
	public BinaryExprNode(ExprNode left, ExprNode right, Operator op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}

	public Operator getOperation() {
		return op;
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

	public enum Operator {
		GT, GE, LT, LE, // Relational operators
		ADD, SUB, MUL, DIV, MOD; // Arithmetic operators
	}
	
}
