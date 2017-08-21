package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;


public class UserTypeNode extends TypeNode {

	public UserTypeNode(String name) {
		super(name);
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}
}
