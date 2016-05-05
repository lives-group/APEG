package apeg.parse.ast.impl;

import apeg.parse.ast.OptionalPegNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class OptionalPegNodeImpl implements OptionalPegNode {
	
	private PegNode peg;
	
	public OptionalPegNodeImpl(PegNode peg) {
		this.peg = peg;
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
