package apeg.parse.ast;

public interface StarPegNode extends PegNode {
	/**
	 * @return parsing expression
	 */
	public PegNode getPeg();
}
