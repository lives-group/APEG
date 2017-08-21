package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class StringExprNode extends ExprNode {
	
	private String value;
	
	public StringExprNode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
