package apeg.parse.ast;

public interface OptionalPegNode extends PegNode {
	/**
	 * @return parsing expression
	 */
	public PegNode getPeg();
}
