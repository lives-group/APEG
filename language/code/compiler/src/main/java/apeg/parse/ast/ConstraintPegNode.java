package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class ConstraintPegNode extends PegNode {

	private ExprNode expr;
	
	public ConstraintPegNode(ExprNode expr) {
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
