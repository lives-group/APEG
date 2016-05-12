package apeg.parse.ast.impl;

import apeg.parse.ast.LiteralPegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class LiteralPegNodeImpl implements LiteralPegNode {

	private String literal;
	
	public LiteralPegNodeImpl(String value) {
		this.literal = value;
	}
	
	@Override
	public String getValue() {
		return literal;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
