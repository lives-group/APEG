package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class NotExprNode extends ExprNode {
	
	private ExprNode expr;
	
	public NotExprNode(ExprNode expr) {
		this.expr = expr;
	}

	public ExprNode getExpr() {
		return expr;
	}

	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
