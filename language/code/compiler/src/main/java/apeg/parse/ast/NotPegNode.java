package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class NotPegNode extends PegNode {
	
	private PegNode peg;
	
	public NotPegNode(PegNode peg) {
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
