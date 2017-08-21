package apeg.parse.ast;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class GrammarTypeNode extends TypeNode {

	public GrammarTypeNode() {
		super("grammar");
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}
}
