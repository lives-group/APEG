package apeg.parse.ast;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class RuleTypeNode extends TypeNode {

	public RuleTypeNode() {
		super("rule");
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}
}
