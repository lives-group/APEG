package apeg.parse.ast.impl;

import apeg.parse.ast.StringExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class StringExprNodeImpl implements StringExprNode {
	
	private String value;
	
	public StringExprNodeImpl(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
