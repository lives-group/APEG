package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class VarDeclarationNode extends ASTNode {
	
	
	private String name;
	private TypeNode type;
	
	public VarDeclarationNode(String name, TypeNode type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public TypeNode getType() {
		return type;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
