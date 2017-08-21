package apeg.parse.ast.impl;

import java.util.List;

import apeg.parse.ast.PegNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.VarDeclarationNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class RuleNodeImpl implements RuleNode {
	
	private String name;
	private Annotation anno;
	private List<VarDeclarationNode> params, returns;
	private PegNode peg;
	
	public RuleNodeImpl(String name, Annotation anno,
			List<VarDeclarationNode> param, List<VarDeclarationNode> returns,
			PegNode peg) {
		this.anno = anno;
		this.name = name;
		this.params = param;
		this.returns = returns;
		this.peg = peg;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Annotation getAnnotation() {
		return anno;
	}

	@Override
	public List<VarDeclarationNode> getParameters() {
		return params;
	}

	@Override
	public List<VarDeclarationNode> getReturns() {
		return returns;
	}

	@Override
	public PegNode getExpr() {
		return peg;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
