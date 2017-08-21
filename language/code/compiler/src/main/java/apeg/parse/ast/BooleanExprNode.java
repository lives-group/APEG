package apeg.parse.ast.impl;

import apeg.parse.ast.BooleanExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class BooleanExprNodeImpl implements BooleanExprNode {

	private boolean value;
	
	public BooleanExprNodeImpl(boolean value) {
		this.value = value;
	}
	
	@Override
	public boolean getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
