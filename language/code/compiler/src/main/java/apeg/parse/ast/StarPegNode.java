package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class StarPegNode extends PegNode {
	
	private PegNode peg;
	
	public StarPegNode(PegNode peg) {
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
