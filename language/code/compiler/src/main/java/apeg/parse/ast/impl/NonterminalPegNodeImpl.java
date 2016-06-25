package apeg.parse.ast.impl;

import java.util.List;

import apeg.parse.ast.ExprNode;
import apeg.parse.ast.NonterminalPegNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class NonterminalPegNodeImpl implements NonterminalPegNode {
	
	private String name;
	private List<ExprNode> attrs;
	
	public NonterminalPegNodeImpl(String name, List<ExprNode> attrs) {
		this.name = name;
		this.attrs = attrs;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<ExprNode> getExprs() {
		return attrs;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
