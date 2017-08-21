package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class BindPegNode extends PegNode {

	private AttributeExprNode attr;
	private PegNode peg;
	
	public BindPegNode(AttributeExprNode attr, PegNode peg) {
		this.attr = attr;
		this.peg = peg;
	}
	
	public String getVariable() {
		return attr.getName();
	}

	public PegNode getPeg() {
		return peg;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
