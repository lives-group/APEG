package apeg.parse.ast.impl;

import apeg.parse.ast.PegNode;
import apeg.parse.ast.StarPegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class StarPegNodeImpl implements StarPegNode {
	
	private PegNode peg;
	
	public StarPegNodeImpl(PegNode peg) {
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
