package apeg.parse.ast.impl;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class TypeNodeImpl implements TypeNode {

	private String type;
	
	public TypeNodeImpl(String type) {
		this.type = type;
	}
	
	@Override
	public String getName() {
		return type;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
