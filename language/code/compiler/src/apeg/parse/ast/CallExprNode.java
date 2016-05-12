package apeg.parse.ast;

import java.util.List;

public interface CallExprNode extends ExprNode {
	/**
	 * @return function name
	 */
	public String getName();
	/**
	 * @return parameters expressions
	 */
	public List<ExprNode> getParameters();
}
