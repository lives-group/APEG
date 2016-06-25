package apeg.parse.ast.impl;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.VarDeclarationNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class VarDeclarationNodeImpl implements VarDeclarationNode {
	
	private String name;
	private TypeNode type;
	
	public VarDeclarationNodeImpl(String name, TypeNode type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TypeNode getType() {
		return type;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
