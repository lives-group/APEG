package apeg.parse.ast;

public interface BindPegNode extends PegNode {
	/**
	 * @return variable name which receives the value of the text matched
	 */
	public String getVariable();
	/**
	 * @return parsing expression
	 */
	public PegNode getPeg();
}
