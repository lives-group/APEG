package apeg.parse.ast;

import apeg.parse.ast.visitor.ElementVisitor;

public interface TypeNode extends ElementVisitor {
	/**
	 * @return type name
	 */
	public String getName();
}
