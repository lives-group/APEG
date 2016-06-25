package apeg.parse.ast;

public interface LiteralPegNode extends PegNode {
	/**
	 * @return literal value
	 */
	public String getValue();
}
