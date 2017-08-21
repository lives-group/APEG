package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class LiteralPegNode extends PegNode {

	private String literal;
	
	public LiteralPegNode(String value) {
		this.literal = value;
	}
	
	public String getValue() {
		return literal;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
