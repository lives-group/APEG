package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AssignmentNode extends ASTNode {

	private AttributeExprNode attr;
	private ExprNode expr;
	
	public AssignmentNode(AttributeExprNode attr, ExprNode expr) {
		this.attr = attr;
		this.expr = expr;
	}
	
	public String getVariable() {
		return attr.getName();
	}

	public ExprNode getExpr() {
		return expr;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
