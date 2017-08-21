package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class PlusPegNode extends PegNode {
	
	private PegNode peg;
	
	public PlusPegNode(PegNode peg) {
		this.peg = peg;
	}

	public PegNode getPeg() {
		return peg;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
