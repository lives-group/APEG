package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class MinusExprNode extends ExprNode {
	
	private ExprNode expr;
	
	public MinusExprNode(ExprNode expr) {
		this.expr = expr;
	}

	public ExprNode getExpr() {
		return expr;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}