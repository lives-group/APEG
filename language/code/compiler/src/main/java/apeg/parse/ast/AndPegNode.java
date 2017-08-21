package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AndPegNode extends PegNode {

	private PegNode peg;
	
	public AndPegNode(PegNode peg) {
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
