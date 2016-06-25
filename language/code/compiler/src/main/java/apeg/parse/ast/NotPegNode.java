package apeg.parse.ast;

public interface NotPegNode extends PegNode {
	/**
	 * @return parsing expression
	 */
	public PegNode getPeg();
}
