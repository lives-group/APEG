package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class RuleNode extends ASTNode {
	
	private String name;
	private Annotation anno;
	private List<VarDeclarationNode> params, returns;
	private PegNode peg;
	
	public RuleNode(String name, Annotation anno,
			List<VarDeclarationNode> param, List<VarDeclarationNode> returns,
			PegNode peg) {
		this.anno = anno;
		this.name = name;
		this.params = param;
		this.returns = returns;
		this.peg = peg;
	}

	public String getName() {
		return name;
	}

	public Annotation getAnnotation() {
		return anno;
	}

	public List<VarDeclarationNode> getParameters() {
		return params;
	}

	public List<VarDeclarationNode> getReturns() {
		return returns;
	}

	public PegNode getExpr() {
		return peg;
	}

	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

	public enum Annotation {
		MEMOIZE, // to memoize intermediate results of a specific rule
		TRANSIENT, // to ignore the intermediate results of a specific rule
		NONE // no annotation
	}
}
