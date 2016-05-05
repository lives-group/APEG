package apeg.parse.ast.impl;

import apeg.parse.ast.AssignmentNode;
import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AssignmentNodeImpl implements AssignmentNode {

	private AttributeExprNode attr;
	private ExprNode expr;
	
	public AssignmentNodeImpl(AttributeExprNode attr, ExprNode expr) {
		this.attr = attr;
		this.expr = expr;
	}
	
	@Override
	public String getVariable() {
		return attr.getName();
	}

	@Override
	public ExprNode getExpr() {
		return expr;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
