package apeg.parse.ast;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class FloatTypeNode extends TypeNode {

	public FloatTypeNode() {
		super("float");
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}
}
