package apeg.parse.ast.impl;

import apeg.parse.ast.NotPegNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class NotPegNodeImpl implements NotPegNode {
	
	private PegNode peg;
	
	public NotPegNodeImpl(PegNode peg) {
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
