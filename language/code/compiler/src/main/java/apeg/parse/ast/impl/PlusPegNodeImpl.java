package apeg.parse.ast.impl;

import apeg.parse.ast.PegNode;
import apeg.parse.ast.PlusPegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class PlusPegNodeImpl implements PlusPegNode {
	
	private PegNode peg;
	
	public PlusPegNodeImpl(PegNode peg) {
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
