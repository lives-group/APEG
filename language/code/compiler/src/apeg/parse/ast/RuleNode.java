package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ElementVisitor;

public interface RuleNode extends ElementVisitor {
	/**
	 * @return rule name
	 */
	public String getName();
	/**
	 * @return rule annotation
	 */
	public Annotation getAnnotation();
	/**
	 * @return set of inherited attributes
	 */
	public List<VarDeclarationNode> getParameters();
	/**
	 * @return set of synthesized attributes
	 */
	public List<VarDeclarationNode> getReturns();
	/**
	 * @return rule parsing expression
	 */
	public PegNode getExpr();
	
	enum Annotation {
		MEMOIZE, // to memoize intermediate results of a specific rule
		TRANSIENT, // to ignore the intermediate results of a specific rule
		NONE // no annotation
	}
}
