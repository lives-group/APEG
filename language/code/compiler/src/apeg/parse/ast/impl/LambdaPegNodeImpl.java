package apeg.parse.ast.impl;

import apeg.parse.ast.LambdaPegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class LambdaPegNodeImpl implements LambdaPegNode {

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
