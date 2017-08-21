package apeg.parse.ast;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class IntExprNode extends ExprNode {
	
	private int value;
	
	public IntExprNode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
