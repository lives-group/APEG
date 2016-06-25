package apeg.parse.ast.impl;

import apeg.parse.ast.AnyPegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class AnyPegNodeImpl implements AnyPegNode {

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
