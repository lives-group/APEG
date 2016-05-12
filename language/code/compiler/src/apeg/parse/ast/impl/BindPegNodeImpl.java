package apeg.parse.ast.impl;

import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.BindPegNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class BindPegNodeImpl implements BindPegNode {

	private AttributeExprNode attr;
	private PegNode peg;
	
	public BindPegNodeImpl(AttributeExprNode attr, PegNode peg) {
		this.attr = attr;
		this.peg = peg;
	}
	
	@Override
	public String getVariable() {
		return attr.getName();
	}

	@Override
	public PegNode getPeg() {
		return peg;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
