package apeg.parse.ast.impl;

import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AttributeExprNodeImpl implements AttributeExprNode {

	private String name;
	
	public AttributeExprNodeImpl(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
