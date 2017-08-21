package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class FloatExprNode extends ExprNode {
	
	private double value;
	
	public FloatExprNode(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
