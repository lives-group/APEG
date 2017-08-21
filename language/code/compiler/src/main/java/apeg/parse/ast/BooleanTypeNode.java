package apeg.parse.ast;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class BooleanTypeNode extends TypeNode {

	public BooleanTypeNode() {
		super("boolean");
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}
}
