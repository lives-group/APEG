package apeg.parse.ast.impl;

import apeg.parse.ast.IntExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class IntExprNodeImpl implements IntExprNode {
	
	private int value;
	
	public IntExprNodeImpl(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
