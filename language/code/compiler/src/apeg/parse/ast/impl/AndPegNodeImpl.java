package apeg.parse.ast.impl;

import apeg.parse.ast.AndPegNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AndPegNodeImpl implements AndPegNode {

	private PegNode peg;
	
	public AndPegNodeImpl(PegNode peg) {
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
