package apeg.parse.ast;

import apeg.parse.ast.visitor.ElementVisitor;

public interface VarDeclarationNode extends ElementVisitor {
	/**
	 * @return variable name
	 */
	public String getName();
	/**
	 * @return variable type
	 */
	public TypeNode getType();
}
