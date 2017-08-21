package apeg.parse.ast;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class IntTypeNode extends TypeNode {

	public IntTypeNode() {
		super("int");
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}
}
