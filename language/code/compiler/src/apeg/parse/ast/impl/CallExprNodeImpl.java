package apeg.parse.ast.impl;

import java.util.List;

import apeg.parse.ast.CallExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class CallExprNodeImpl implements CallExprNode {

	private String name;
	private List<ExprNode> params;
	
	public CallExprNodeImpl(String name, List<ExprNode> param) {
		this.name = name;
		this.params = param;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<ExprNode> getParameters() {
		return params;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
