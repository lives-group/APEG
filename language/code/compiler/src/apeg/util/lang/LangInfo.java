package apeg.util.lang;

/**
 * This interface provides all informations needed to generate
 * code and extract/compile code from external functions
 * 
 * @author Leonardo Reis
 */
public interface LangInfo {
	/**
	 * @return The name of the language (e.g. Java, Haskell, so forth)
	 */
	public String languageName();
	/**
	 * @return The extension of the language
	 *  (e.g. java for the Java language, hs for Haskell)
	 */
	public String extension();
	/**
	 * Check if module if a valid module name
	 * @param module The module name
	 * @return true if the name is valid, otherwise return false
	 */
	//public boolean isModuleName(String module);
	/**
	 * Convert the name of the module in an directory representation
	 *  for example, the package name of Java "apeg.common.lang" returns
	 *  "apeg/common/lang"
	 * @param module A module name
	 * @return directory for the module
	 */
	public String moduleToDir(String module);
	/**
	 * @return the parser to extract type information of the external functions
	 */
	public ExternalParser parser();

}
