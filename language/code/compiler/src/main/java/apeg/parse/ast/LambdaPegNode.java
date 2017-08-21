package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class LambdaPegNode extends PegNode {

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
