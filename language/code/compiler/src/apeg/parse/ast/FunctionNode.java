package apeg.parse.ast;

import apeg.parse.ast.visitor.ElementVisitor;

public interface FunctionNode extends ElementVisitor {
	/**
	 * @return the function name
	 */
	public String getName();
	
}
