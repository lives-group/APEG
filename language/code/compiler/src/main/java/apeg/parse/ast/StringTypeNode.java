package apeg.parse.ast;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class StringTypeNode extends TypeNode {

	public StringTypeNode() {
		super("string");
	}
	
	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}
}
