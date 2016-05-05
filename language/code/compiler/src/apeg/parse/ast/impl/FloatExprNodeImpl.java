package apeg.parse.ast.impl;

import apeg.parse.ast.FloatExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class FloatExprNodeImpl implements FloatExprNode {
	
	private double value;
	
	public FloatExprNodeImpl(double value) {
		this.value = value;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
