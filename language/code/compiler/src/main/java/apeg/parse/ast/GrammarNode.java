package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ElementVisitor;

/**
 * A node representing a definition of an APEG grammar
 * @author Leonardo Reis
 */
public interface GrammarNode extends ElementVisitor {
	/**
	 * @return grammar name
	 */
	public String getName();
	/**
	 * @return possible grammar option, e.g., whether it is adaptable.
	 */
	public List<GrammarOption> getOptions();
	/**
	 * @return any code (unchecked string) to be inserted
	 * at the begin of the generated code
	 */
	public String getPreamble();
	/**
	 * @return set of external functions that can be used in expressions
	 */
	public List<String> getFunctions();
	/**
	 * 
	 * @return set of file where to find external functions
	 */
	public List<String> getFunctionsSources();	
	/**
	 * @return set of grammar rules
	 */
	public List<RuleNode> getRules();
	
	public enum GrammarOption {
		ADAPTABLE, // if the grammar is adaptable
		NO_ADAPTABLE, // if the grammar is not adaptable
		MEMOIZE, // if it is to memoize intermediate results
		NO_MEMOIZE, // if it is not to memoize intermediate results
		USUAL_SEMANTICS, // if it is to use conventional APEG choice semantics
		SIMPLE_ENV_SEMANTICS // if it is to use non conventional APEG semantics
	}
}
